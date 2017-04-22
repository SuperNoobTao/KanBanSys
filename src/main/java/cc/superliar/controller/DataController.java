package cc.superliar.controller;

import cc.superliar.annotation.CurrentUser;
import cc.superliar.component.Transformer;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.ResultConstant;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.DataDomain;
import cc.superliar.domain.StyleDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.DeviceParam;
import cc.superliar.po.Device;
import cc.superliar.po.Url;
import cc.superliar.po.User;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.vo.DataVO;
import cc.superliar.vo.StyleVO;
import cc.superliar.vo.UrlVO;
import org.apache.commons.lang3.StringUtils;
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
 * Created by shentao on 2017/3/1.
 */
@RestController
@RequestMapping("/device")
public class DataController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------


    @Autowired
    private DataDomain dataDomain;
    @Autowired
    private Transformer transformer;
    @Autowired
    private StyleDomain styleDomain;

    /**
     * android设备获取某id的配置信息
     * @param id
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity update(@PathVariable String id) throws Exception {

            return new ResponseEntity<>(dataDomain.result(id), HttpStatus.OK);


    }



}
