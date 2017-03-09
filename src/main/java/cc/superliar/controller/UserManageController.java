package cc.superliar.controller;

import cc.superliar.component.ResultHelper;
import cc.superliar.component.ValidateHelper;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import cc.superliar.domain.RoleDomain;
import cc.superliar.domain.UserDomain;
import cc.superliar.enums.ErrorType;
import cc.superliar.po.User;
import cc.superliar.repo.NativeSQLReposity;
import cc.superliar.vo.RoleVO;
import cc.superliar.vo.UserManageVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.events.ProcessingInstruction;
import java.util.List;

/**
 * Created by shentao on 2017/3/9.
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.USERS+"/details")
public class UserManageController {



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
}
