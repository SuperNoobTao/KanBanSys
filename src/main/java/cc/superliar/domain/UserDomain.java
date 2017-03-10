package cc.superliar.domain;

import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.constant.ResourceConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.OperationType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.UserParam;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import cc.superliar.repo.UserReposity;
import cc.superliar.util.BeanUtils;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
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
     * @param param       {@link UserParam}
     * @return {@link UserVO}
     * @throws CommonsException {@link ErrorType#SYS0111} user already existing, usr taken.
     */
    @Transactional public UserVO create(UserParam param) throws Exception {
        User user = new User();
        user.setAccount(param.getAccount());
        user.setName(param.getName());
        user.setPwd(passwordEncoder.encode(param.getPwd()));
        userRepository.save(user);
        UserVO userVO = new UserVO();
        if (userRepository.findById(user.getId())!=null){

            userVO.setName(user.getName());
            userVO.setAccount(user.getAccount());
            userVO.setId(user.getId());
            userVO.setDescription(user.getDescription());
            return  userVO;
        }
        return userVO;
    }


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
    @Transactional
    public UserVO update(UserParam param, User currentUser) throws Exception {
        User user = findByIdAndValidFlag(param.getId());
        //当 account不为空且account改变时需要确认重复与否
        if (StringUtils.isNotBlank(param.getAccount()) && !param.getAccount().equals(user.getAccount())) {
            usrExists(param.getAccount());
        }
        return super.updateByPO(UserVO.class, userParam2PO(param, user, currentUser), currentUser);
    }

    public User findByIdAndValidFlag(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    /**
     * Delete <T>, update valid flag to invalid.
     *
     * @param inputParam  input param
     * @param currentUser current user
     * @throws Exception
     */

    @Transactional
    public void delete(UserParam inputParam, User currentUser) throws Exception {
        User po = findByIdParam(inputParam);
        BeanUtils.copyPropertiesIgnoreNull(inputParam, po);
//    Field lastModifiedByField = po.getClass().getDeclaredField(CommonsConstant.LAST_MODIFIED_BY);
//    lastModifiedByField.setAccessible(true);
//    lastModifiedByField.set(po, currentUser.getId());
        Field lastModifiedDateField = po.getClass().getDeclaredField(CommonsConstant.LAST_MODIFIED_DATE);
        lastModifiedDateField.setAccessible(true);
        lastModifiedDateField.set(po, LocalDateTime.now());
        logHelper.logUsersOperations(OperationType.DELETE, getClassT().getName(), currentUser);
        userRepository.save(setInvalid(po));
        userManageDomain.deleteByUserId(po.getId());
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired private RoleDomain roleDomain;

    @Autowired private UserReposity userRepository;

    @Autowired private CustomPasswordEncoder passwordEncoder;

    @Autowired private Transformer transformer;

    @Autowired private UserManageDomain userManageDomain;




    @Autowired public UserDomain(UserReposity userRepository) {
        this.userRepository = userRepository;
    }

    private static final String USR = "account";

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


    /**
     * 如果有相同的账号则跳出错误
     * @param usr
     * @throws Exception
     */
    private void usrExists(String usr) throws Exception {
        if (userRepository.findByAccountAndValidFlag(usr, ValidFlag.VALID).isPresent()) {
            // Throw user already exists error, usr taken.
            throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, ResourceConstant.USERS, USR));
        }
    }


    /**
     * Set invalid flag
     *
     * @param po po
     * @return po with invalid flag
     * @throws Exception
     */
    private User setInvalid(User po) throws Exception {
        Field validFlagField = po.getClass().getDeclaredField(CommonsConstant.VALID_FLAG);
        validFlagField.setAccessible(true);
        validFlagField.set(po, ValidFlag.INVALID);
        return po;
    }
}
