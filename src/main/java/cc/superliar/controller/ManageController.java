package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.Transformer;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.DataDomain;
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
import cc.superliar.util.MqttServer;
import cc.superliar.vo.DataVO;
import cc.superliar.vo.ManageVO;
import cc.superliar.vo.UrlVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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
     * 增加某一设备的链接
     * @param param {@link ManageParam}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity create(@CurrentUser User currentUser, @Valid ManageParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            ManageVO manageVO =  manageDomain.create(param,currentUser);
            // send msg
            String deviceid = param.getDevice();
            DataVO dataVO = dataDomain.result(deviceid);
            JSONObject object = JSONObject.fromObject(dataVO);
            String jsonStr = object.toString();
            MqttServer.sendMqtt(deviceid,jsonStr);
            // Return result and message.
            return new ResponseEntity<>(manageVO, HttpStatus.CREATED);
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
            List<ManageVO> list = nativeSQLReposity.listbydevice(device.getId());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Delete manage {@link Device}.
     * 批量删除数据
     * @param  {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity manageDelete(@CurrentUser User currentUser, HttpServletRequest request) {
        try {
            String id = null;
            List<String> idList = new ArrayList<String>();
            System.out.println(request.getParameter("input"));
            JSONArray jsonArr = JSONArray.fromObject(request.getParameter("input"));
            for(Object obj : jsonArr){
                JSONObject jso = JSONObject.fromObject(obj);
                id=jso.get("Id").toString();
                idList.add( id );//id列表
            }
            String deviceid= manageDomain.findDeviceById(id);
            manageDomain.deleteManages(idList, currentUser);
            // send msg
            DataVO dataVO = dataDomain.result(deviceid);
            JSONObject object = JSONObject.fromObject(dataVO);
            String jsonStr = object.toString();
            MqttServer.sendMqtt(deviceid,jsonStr);

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

    @Autowired
    private DataDomain dataDomain;
}
