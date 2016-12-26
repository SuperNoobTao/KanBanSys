package cc.superliar.domain;

import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.DeviceParam;
import cc.superliar.param.ManageParam;
import cc.superliar.param.RoleParam;
import cc.superliar.param.UserParam;
import cc.superliar.po.*;
import cc.superliar.repo.DeviceReposity;
import cc.superliar.repo.ManageReposity;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.DeviceVO;
import cc.superliar.vo.ManageVO;
import cc.superliar.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shentao on 2016/12/26.
 */
@Service
@Transactional(readOnly = true)
public class ManageDomain extends BaseDomain<Manage,Integer> {


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link Role}.
     *
     * @param currentUser current user
     * @param param       {@link RoleParam}
     * @return {@link RoleVO}
     * @throws CommonsException {@link ErrorType#SYS0111} user already existing, usr taken.
     */
    @Transactional
    public ManageVO create(ManageParam param, User currentUser) throws Exception {
        Manage manage = new Manage(param.getUrl(),param.getDevice());
        return super.createByPO(ManageVO.class, manage, currentUser);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired private ManageReposity manageReposity;

    @Autowired private CustomPasswordEncoder passwordEncoder;

    @Autowired private Transformer transformer;

    /**
     * Transform {@link UserParam} to {@link User}.
     *
     * @param param       {@link UserParam}
     * @param manage        {@link User}
     * @param currentUser currentUser
     * @return {@link User}
     */
    private Manage manageParam2PO(ManageParam param, Manage manage, User currentUser) throws Exception {
        transformer.param2PO2(getClassT(), param, manage, currentUser);
        return manage;
    }



}
