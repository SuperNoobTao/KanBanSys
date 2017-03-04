package cc.superliar.domain;

import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.DeviceParam;

import cc.superliar.param.RoleParam;
import cc.superliar.param.UserParam;
import cc.superliar.po.Device;
import cc.superliar.po.Role;
import cc.superliar.po.Url;

import cc.superliar.po.User;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.ManageReposity;
import cc.superliar.util.BeanUtils;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.DeviceVO;
import cc.superliar.vo.RoleVO;
import cc.superliar.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by shentao on 2016/12/20.
 */
@Service
@Transactional(readOnly = true)
public class DeviceDomain extends BaseDomain<Device,String> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Delete <T>, update valid flag to invalid.
     *
     *
     * @param currentUser current user
     * @throws Exception
     */
    @Transactional public void deleteList(List<String> isList, User currentUser) throws Exception {
        for(String id : isList) {
            DeviceParam inputParam = new DeviceParam(StringUtils.isBlank(id) ? null : id);
            Device po = findByIdParam(inputParam);

            BeanUtils.copyPropertiesIgnoreNull(inputParam, po);

            Field lastModifiedDateField = po.getClass().getDeclaredField(CommonsConstant.LAST_MODIFIED_DATE);
            lastModifiedDateField.setAccessible(true);
            lastModifiedDateField.set(po, LocalDateTime.now());
            logHelper.logUsersOperations(OperationType.DELETE, getClassT().getName(), currentUser);
            deviceReposity.save(setInvalid(po));//设置设备可用字段为0
            manageDomain.deleteByDeviceId(id);//删除对应

        }
    }








    /**
     * Create new {@link Role}.
     *
     * @param currentUser current user
     * @param param       {@link RoleParam}
     * @return {@link RoleVO}
     * @throws CommonsException {@link ErrorType#SYS0111} user already existing, usr taken.
     */
    @Transactional
    public DeviceVO create(DeviceParam param, User currentUser) throws Exception {
        idExists(param.getId());
        return super.createByPO(DeviceVO.class, deviceParam2PO(param, new Device(), currentUser), currentUser);
    }



    /**
     * Update {@link User}.
     *
     * @param param {@link UserParam}
     * @return {@link UserVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by id param.
     */
    @Transactional public DeviceVO update(DeviceParam param, User currentUser) throws Exception {
        Device device = findById(param.getId());
        //当id不为空或者id改变时需要确认重复与否
        if (StringUtils.isNotBlank(param.getId()) && !param.getId().equals(device.getId())) {
            idExists(param.getId());
        }
        return super.updateByPO(DeviceVO.class, deviceParam2PO(param, device, currentUser), currentUser);
    }




    public Device findById(String id) {
        return deviceReposity.findById(id).orElse(null);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired private UrlDomain urlDomain;

    @Autowired private DeviceReposity deviceReposity;

    @Autowired private ManageReposity manageReposity;

    @Autowired private CustomPasswordEncoder passwordEncoder;

    @Autowired private Transformer transformer;

    @Autowired private ManageDomain manageDomain;

    @Autowired public DeviceDomain(DeviceReposity deviceReposity) {
        this.deviceReposity = deviceReposity;
    }


    /**
     * Transform {@link UserParam} to {@link User}.
     *
     * @param param       {@link UserParam}
     * @param user        {@link User}
     * @param currentUser currentUser
     * @return {@link User}
     */
    private Device deviceParam2PO(DeviceParam param, Device device, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, device, currentUser);
        if (!StringUtils.isBlank(param.getUrlIds())) {
            List<Url> urls = urlDomain.getAllByIds(transformer.idsStr2List(param.getUrlIds()));
            device.setUrls(transformer.list2Set(urls));
        }
        return device;
    }


    private void idExists(String id) throws Exception {
        if (deviceReposity.findByIdAndValidFlag(id, ValidFlag.VALID).isPresent()) {
            // Throw role already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.ID));
        }
    }


    /**
     * Delete <T>, update valid flag to invalid.
     *
     * @param inputParam  input param
     * @param currentUser current user
     * @throws Exception
     */
    @Transactional public void delete2(int id, User currentUser) throws Exception {

        logHelper.logUsersOperations(OperationType.DELETE, getClassT().getName(), currentUser);
        manageReposity.delete(id);
    }


    /**
     * Set invalid flag
     *
     * @param po po
     * @return po with invalid flag
     * @throws Exception
     */
    private Device setInvalid(Device po) throws Exception {
        Field validFlagField = po.getClass().getDeclaredField(CommonsConstant.VALID_FLAG);
        validFlagField.setAccessible(true);
        validFlagField.set(po, ValidFlag.INVALID);
        return po;
    }



}
