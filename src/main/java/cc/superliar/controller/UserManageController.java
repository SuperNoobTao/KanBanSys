package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.RoleDomain;
import cc.superliar.domain.UserDomain;
import cc.superliar.domain.UserManageDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.UserManageParam;
import cc.superliar.po.User;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.vo.RoleVO;
import cc.superliar.vo.UserManageVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
import javax.xml.stream.events.ProcessingInstruction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shentao on 2017/3/9.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.USERS+"/details")
public class UserManageController {


    /**
     * 增加某一用户的角色
     * @param param {@link UserManageParam}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity create(@CurrentUser User currentUser, @Valid UserManageParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Return result and message.
            return new ResponseEntity<>(userManageDomain.create(param, currentUser), HttpStatus.CREATED);
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
     * 显示某一用户的角色列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity detailroles(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return new ResponseEntity<>(roleDomain.getAll(null, null, RoleVO.class), HttpStatus.OK);
            }
            User user = userDomain.getById(Long.valueOf(id), User.class);
            List<UserManageVO> list = nativeSQLReposity.listbyuser(user.getId());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Delete manage {@link }.
     * 批量删除数据
     * @param  {@link Device#id}
     * @return {@link cc.superliar.vo.DeviceVO}
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity userManageDelete(@CurrentUser User currentUser, HttpServletRequest request) {
        try {

            List<String> idList = new ArrayList<String>();
            System.out.println(request.getParameter("input"));
            JSONArray jsonArr = JSONArray.fromObject(request.getParameter("input"));
            for(Object obj : jsonArr){
                JSONObject jso = JSONObject.fromObject(obj);
                idList.add( jso.get("Id").toString() );//id列表
            }
            userManageDomain.deleteUserManages(idList, currentUser);
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
    private RoleDomain roleDomain;

    @Autowired
    private UserDomain userDomain;

    @Autowired
    private NativeSQLReposity nativeSQLReposity;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private UserManageDomain userManageDomain;
}
