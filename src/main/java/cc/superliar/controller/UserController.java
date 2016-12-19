package cc.superliar.controller;


import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ControllerConstant;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.UserDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.UserParam;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import cc.superliar.util.QueryHelper;
import cc.superliar.vo.UserVO;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Created by shentao on 2016/11/30.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.USERS)
public class UserController {


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link User}.
     *
     * @param param {@link UserParam}
     * @return {@link com.saintdan.framework.vo.UserVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@CurrentUser User currentUser, @Valid UserParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Return result and message.
            return new ResponseEntity<>(userDomain.create(param, currentUser), HttpStatus.CREATED);
//      return new ResponseEntity<>(userDomain.create(param, currentUser), HttpStatus.CREATED);
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
     *
     * @param param {@link UserParam}
     * @return users
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity all(
            @And({
                    @Spec(path = "account", spec = Like.class),
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
                    @Spec(path = "createdDate", params = {"createdDateAfter", "createdDateBefore"}, spec = DateBetween.class)}) Specification<User> userSpecification,
            UserParam param
    ) {
        try {
            if (param.getPageNo() == null) {
                return new ResponseEntity<>(userDomain.getAll(userSpecification, QueryHelper.getSort(param.getSortBy()), UserVO.class), HttpStatus.OK);
            }
            return new ResponseEntity<>(userDomain.getPage(userSpecification, QueryHelper.getPageRequest(param), UserVO.class), HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Show {@link com.saintdan.framework.vo.UserVO} by ID.
     *
     * @param id {@link User#id}
     * @return {@link com.saintdan.framework.vo.UserVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity detail(@PathVariable String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(userDomain.getById(Long.valueOf(id), UserVO.class), HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Update {@link User}.
     *
     * @param id    {@link User#id}
     * @param param {@link UserParam}
     * @return {@link com.saintdan.framework.vo.UserVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@CurrentUser User currentUser, @PathVariable String id, @Valid UserParam param, BindingResult result) {
        try {
            param.setId(StringUtils.isBlank(id) ? null : Long.valueOf(id));
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.UPDATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Update user.
            return new ResponseEntity<>(userDomain.update(param, currentUser), HttpStatus.OK);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete {@link User}.
     *
     * @param id {@link User#id}
     * @return {@link com.saintdan.framework.vo.UserVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@CurrentUser User currentUser, @PathVariable String id) {
        try {
            UserParam param = new UserParam(StringUtils.isBlank(id) ? null : Long.valueOf(id));
            // Validate current user and param.
            ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.DELETE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Delete user.
            userDomain.delete(param, currentUser);
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
    private UserDomain userDomain;




}
