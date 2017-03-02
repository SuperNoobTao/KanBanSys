package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.ResultHelper;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ControllerConstant;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.StyleDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.StyleParam;

import cc.superliar.po.Style;

import cc.superliar.po.User;
import cc.superliar.util.QueryHelper;
import cc.superliar.vo.StyleVO;

import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shentao on 2016/12/21.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.STYLES)
public class StyleController {


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link Style}.
     * 创建一个显示样式
     * @param param {@link StyleParam}
     * @return {@link cc.superliar.vo.StyleVO}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@CurrentUser User currentUser, @Valid StyleParam param, BindingResult result) {
        try {
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.CREATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Return result and message.
            return new ResponseEntity<>(styleDomain.create(param, currentUser), HttpStatus.CREATED);
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
     * 显示全部样式
     * @param param {@link StyleParam}
     * @return styles
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity all(
            @And({
                    @Spec(path = "validFlag", constVal = "VALID", spec = In.class),
                    @Spec(path = "createdDate", params = {"createdDateAfter", "createdDateBefore"}, spec = DateBetween.class)}) Specification<Style> styleSpecification,
            StyleParam param
    ) {
        try {
            if (param.getPage() == null) {
                return new ResponseEntity<>(styleDomain.getAll(styleSpecification, QueryHelper.getSort(param.getSortBy()), StyleVO.class), HttpStatus.OK);
            }
            Page<Style> deviceList = styleDomain.getPage(styleSpecification, QueryHelper.getPageRequest(param), StyleVO.class);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("total",deviceList.getTotalElements());
            map.put("rows",deviceList.getContent());
            return  new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Show {@link cc.superliar.vo.StyleVO} by ID.
     * 显示某个ID的显示样式
     * @param id {@link Style#id}
     * @return {@link cc.superliar.vo.StyleVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity detail(@PathVariable String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(styleDomain.getById(Long.valueOf(id), StyleVO.class), HttpStatus.OK);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Update {@link Style}.
     * 更新某个显示样式
     * @param id    {@link Style#id}
     * @param param {@link StyleParam}
     * @return {@link cc.superliar.vo.StyleVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@CurrentUser User currentUser, @PathVariable String id, @Valid StyleParam param, BindingResult result) {
        try {
            param.setId(StringUtils.isBlank(id) ? null : Long.valueOf(id));
            // Validate current user, param and sign.
            ResponseEntity responseEntity = validateHelper.validate(param, result, currentUser, logger, OperationType.UPDATE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Update user.
            return new ResponseEntity<>(styleDomain.update(param, currentUser), HttpStatus.OK);
        } catch (CommonsException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(logger, e.getErrorType(), e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete {@link Style}.
     * 删除某个显示样式
     * @param id {@link Style#id}
     * @return {@link cc.superliar.vo.StyleVO}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@CurrentUser User currentUser, @PathVariable String id) {
        try {
            StyleParam param = new StyleParam(StringUtils.isBlank(id) ? null : Long.valueOf(id));
            // Validate current user and param.
            ResponseEntity responseEntity = validateHelper.validate(param, currentUser, logger, OperationType.DELETE);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            // Delete user.
            styleDomain.delete(param, currentUser);
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

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired private ValidateHelper validateHelper;

    @Autowired private StyleDomain styleDomain;
}
