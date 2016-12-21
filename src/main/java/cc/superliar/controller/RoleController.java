package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ControllerConstant;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.RoleDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.RoleParam;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import cc.superliar.util.QueryHelper;
import cc.superliar.vo.RoleVO;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
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
 * Created by shentao on 2016/12/19.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.ROLES)
public class RoleController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link cc.superliar.po.Role}.
     *
     * @param param {@link RoleParam}
     * @return {@link cc.superliar.vo.RoleVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@CurrentUser User currentUser, @Valid RoleParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Return result and message.
            return new ResponseEntity<>(roleDomain.create(param, currentUser), HttpStatus.CREATED);
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
     * @param param {@link RoleParam}
     * @return roles.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity all(
            @And({
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
                    @Spec(path = "createdDate", params = {"createdDateAfter, createdDateBefore"}, spec = DateBetween.class)}) Specification<Role> roleSpecification,
            RoleParam param) {
        try {
            if (param.getPageNo() == null) {
                return new ResponseEntity<>(roleDomain.getAll(roleSpecification, QueryHelper.getSort(param.getSortBy()), RoleVO.class), HttpStatus.OK);
            }
            return new ResponseEntity<>(roleDomain.getPage(roleSpecification, QueryHelper.getPageRequest(param), RoleVO.class), HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Show {@link cc.superliar.vo.RoleVO} by ID.
     *
     * @param id {@link Role#id}
     * @return {@link cc.superliar.vo.RoleVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity detail(@PathVariable String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(roleDomain.getById(Long.valueOf(id), RoleVO.class), HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Update {@link cc.superliar.po.Role}.
     *
     * @param id    {@link Role#id}
     * @param param {@link RoleParam}
     * @return {@link cc.superliar.vo.RoleVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@CurrentUser User currentUser, @PathVariable String id, @Valid RoleParam param, BindingResult result) {
        try {
            param.setId(StringUtils.isBlank(id) ? null : Long.valueOf(id));
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.UPDATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Update role.
            return new ResponseEntity<>(roleDomain.update(param, currentUser), HttpStatus.OK);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Delete {@link cc.superliar.po.Role}.
     *
     * @param id {@link Role#id}
     * @return {@link cc.superliar.vo.RoleVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@CurrentUser User currentUser, @PathVariable String id) {
        try {
            RoleParam param = new RoleParam(StringUtils.isBlank(id) ? null : Long.valueOf(id));
            // Validate current user and param.
            ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.DELETE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Delete role.
            roleDomain.delete(param, currentUser);
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

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired private ValidateHelper validateHelper;

    @Autowired private RoleDomain roleDomain;
}
