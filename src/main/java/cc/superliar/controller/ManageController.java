package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.Transformer;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.DeviceDomain;
import cc.superliar.domain.ManageDomain;
import cc.superliar.domain.UrlDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.DeviceParam;
import cc.superliar.param.ManageParam;
import cc.superliar.po.Device;
import cc.superliar.po.User;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.vo.ManageVO;
import cc.superliar.vo.UrlVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理设备链接列表
 * Created by shentao on 2016/12/26.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.DEVICES+"/details")

public class ManageController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link Manage}.
     * 增加某一设备的链接
     * @param param {@link ManageParam}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@CurrentUser User currentUser, @Valid ManageParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Return result and message.
            return new ResponseEntity<>(manageDomain.create(param, currentUser), HttpStatus.CREATED);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Show urls by deviceID.
     * 显示 某一ID 设备的 链接 列表
     * @param id {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity detailUrls(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return new ResponseEntity<>(urlDomain.getAll(null, null, UrlVO.class), HttpStatus.OK);
            }
            Device device = deviceDomain.getByIdStr(id, Device.class);
//            Set<Url> urls = device.getUrls();
//            List<UrlVO> urlVOS = transformer.pos2VOs(UrlVO.class,transformer.set2List(urls));
            List<ManageVO> list = nativeSQLReposity.listbydevice(device.getId());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Delete manage {@link Device}.
     * 删除 一条 对应的 数据
     * @param  {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity manageDelete(@CurrentUser User currentUser, @PathVariable String id, @Valid DeviceParam param, BindingResult result) {
        try {

            int  idInt = Integer.parseInt(id);
            // Delete user.
            deviceDomain.delete2(idInt, currentUser);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UrlDomain urlDomain;

    @Autowired
    private DeviceDomain deviceDomain;

    @Autowired
    private NativeSQLReposity nativeSQLReposity;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private ManageDomain manageDomain;

    @Autowired
    private Transformer transformer;

}
