package cc.superliar.domain;

import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.component.Transformer;
import cc.superliar.constant.ResourceConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.UserParam;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import cc.superliar.repo.UserReposity;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shentao on 2016/11/29.
 */
@Service
@Transactional(readOnly = true)
public class UserDomain extends BaseDomain<User,Long> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new {@link User}.
     *
     * @param currentUser current user
     * @param param       {@link UserParam}
     * @return {@link UserVO}
     * @throws CommonsException {@link ErrorType#SYS0111} user already existing, usr taken.
     */
    @Transactional public UserVO create(UserParam param, User currentUser) throws Exception {
        usrExists(param.getAccount());
        return super.createByPO(UserVO.class, userParam2PO(param, new User(), currentUser), currentUser);
    }

    /**
     * Get {@link UserVO} by user's usr.
     *
     * @param param {@link UserParam}
     * @return {@link UserVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by usr param.
     */
    public UserVO getUserByUsr(UserParam param) throws Exception {
        return transformer.po2VO(UserVO.class, findByUsr(param.getAccount()));
    }

    public User findByUsr(String usr) throws Exception {
        return userRepository.findByAccountAndValidFlag(usr, ValidFlag.VALID).orElse(null);
    }

    /**
     * Update {@link User}.
     *
     * @param param {@link UserParam}
     * @return {@link UserVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by id param.
     */
    @Transactional public UserVO update(UserParam param, User currentUser) throws Exception {
        User user = findByIdAndValidFlag(param.getId());
        if (StringUtils.isNotBlank(param.getAccount()) && param.getAccount().equals(user.getAccount())) {
            usrExists(param.getAccount());
        }
        return super.updateByPO(UserVO.class, userParam2PO(param, user, currentUser), currentUser);
    }

    public User findByIdAndValidFlag(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired private RoleDomain roleDomain;

    @Autowired private UserReposity userRepository;

    @Autowired private CustomPasswordEncoder passwordEncoder;

    @Autowired private Transformer transformer;

    @Autowired public UserDomain(UserReposity userRepository) {
        this.userRepository = userRepository;
    }

    private static final String USR = "usr";

    /**
     * Transform {@link UserParam} to {@link User}.
     *
     * @param param       {@link UserParam}
     * @param user        {@link User}
     * @param currentUser currentUser
     * @return {@link User}
     */
    private User userParam2PO(UserParam param, User user, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, user, currentUser);
        if (!StringUtils.isBlank(param.getRoleIds())) {
            List<Role> roles = roleDomain.getAllByIds(transformer.idsStr2List(param.getRoleIds()));
            user.setRoles(transformer.list2Set(roles));
        }
        if (!StringUtils.isBlank(param.getPwd())) {
            user.setPwd(passwordEncoder.encode(param.getPwd()));
        }
        return user;
    }

    private void usrExists(String usr) throws Exception {
        if (userRepository.findByAccountAndValidFlag(usr, ValidFlag.VALID).isPresent()) {
            // Throw user already exists error, usr taken.
            throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, ResourceConstant.USERS, USR));
        }
    }
}
