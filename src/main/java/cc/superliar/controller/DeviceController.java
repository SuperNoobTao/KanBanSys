package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.Transformer;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ControllerConstant;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.DeviceDomain;

import cc.superliar.domain.UrlDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.DeviceParam;

import cc.superliar.po.Device;

import cc.superliar.po.User;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.util.QueryHelper;

import cc.superliar.vo.DeviceVO;
import cc.superliar.vo.ManageVO;
import cc.superliar.vo.UrlVO;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.*;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.DEVICES)
public class DeviceController {


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * 创建一个设备
     *
     * @param param {@link DeviceParam}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@CurrentUser User currentUser, @Valid DeviceParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Return result and message.
            return new ResponseEntity<>(deviceDomain.create(param, currentUser), HttpStatus.CREATED);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Show all.
     * 分页显示设备列表
     * @param param {@link DeviceParam}
     * @return devices
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity all(
            @And({
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
                    @Spec(path = "createdDate", params = {"createdDateAfter", "createdDateBefore"}, spec = DateBetween.class)}) Specification<Device> deviceSpecification,
            DeviceParam param
    ) {
        try {

            if (param.getPage() == null) {
                return new ResponseEntity<>(deviceDomain.getAll(deviceSpecification, QueryHelper.getSort(param.getSortBy()), DeviceVO.class), HttpStatus.OK);
            }

            Page<Device> deviceList = deviceDomain.getPage(deviceSpecification, QueryHelper.getPageRequest(param), DeviceVO.class);
            Map<String,java.lang.Object> map = new HashMap<String,Object>();
            map.put("total",deviceList.getTotalElements());
            map.put("rows",deviceList.getContent());

            return  new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Show {@link cc.superliar.vo.DeviceVO} by ID.
     * 显示某id的设备的信息
     * @param id {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity detail(@PathVariable String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(deviceDomain.getByIdStr(id, DeviceVO.class), HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /**
     * 显示可以添加的urls
     *
     * @param id {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(value = "/combox", method = RequestMethod.GET)
    public ResponseEntity canAddUrls(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return new ResponseEntity<>(urlDomain.getAll(null, null, UrlVO.class), HttpStatus.OK);
            }
            Device device = deviceDomain.getByIdStr(id, Device.class);
//            Set<Url> urls = device.getUrls();
//            List<UrlVO> urlVOS = transformer.pos2VOs(UrlVO.class,transformer.set2List(urls));
            List<UrlVO> list = nativeSQLReposity.listbydevice2(device.getId());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    /**
     * Update {@link Device}.
     *
     * @param id    {@link Device#id}
     * @param param {@link DeviceParam}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@CurrentUser User currentUser, @PathVariable String id, @Valid DeviceParam param, BindingResult result) {
        try {
            param.setId(StringUtils.isBlank(id) ? null : id);
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.UPDATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Update user.
            return new ResponseEntity<>(deviceDomain.update(param, currentUser), HttpStatus.OK);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete {@link Device}.
     *
     * @param id {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@CurrentUser User currentUser, @PathVariable String id) {
        try {
            DeviceParam param = new DeviceParam(StringUtils.isBlank(id) ? null : id);
            // Validate current user and param.
            ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.DELETE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Delete user.
            deviceDomain.delete(param, currentUser);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 批量删除
     *
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity deleteList(@CurrentUser User currentUser, HttpServletRequest request) {
        try {

            List<String> idList = new ArrayList<String>();
            System.out.println(request.getParameter("input"));
            JSONArray jsonArr = JSONArray.fromObject(request.getParameter("input"));
            for(Object obj : jsonArr){
                JSONObject jso = JSONObject.fromObject(obj);
                idList.add( jso.get("Id").toString() );//id列表
            }

            // Delete user. 遍历ID，批量删除
            deviceDomain.deleteList(idList, currentUser);
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
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private DeviceDomain deviceDomain;

    @Autowired
    private Transformer transformer;

    @Autowired
    private UrlDomain urlDomain;

    @Autowired
    private NativeSQLReposity nativeSQLReposity;
}
