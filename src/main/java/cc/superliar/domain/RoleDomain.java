package cc.superliar.domain;

import cc.superliar.component.Transformer;
import cc.superliar.constant.CommonsConstant;
import cc.superliar.enums.ErrorType;
import cc.superliar.enums.ValidFlag;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.RoleParam;
import cc.superliar.po.Resource;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import cc.superliar.repo.RoleRepository;
import cc.superliar.util.ErrorMsgHelper;
import cc.superliar.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by shentao on 2016/12/9.
 */
@Service
@Transactional(readOnly = true)
public class RoleDomain extends BaseDomain<Role, Long> {
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
    public RoleVO create(RoleParam param, User currentUser) throws Exception {
        nameExists(param.getName());
        return super.createByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
    }

    /**
     * Get {@link RoleVO} by name of role.
     *
     * @param param {@link RoleParam}
     * @return {@link RoleVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by name param.
     */
    public RoleVO getRoleByName(RoleParam param) throws Exception {
        return transformer.po2VO(RoleVO.class, findByName(param.getName()));
    }

    public Role findByName(String name) throws Exception {
        return roleRepository.findByNameAndValidFlag(name, ValidFlag.VALID).orElse(null);
    }

    /**
     * Update {@link Role}.
     *
     * @param param {@link RoleParam}
     * @return {@link RoleVO}
     * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by id param.
     */
    @Transactional public RoleVO update(RoleParam param, User currentUser) throws Exception {
        Role role = findById(param.getId());
        if (param.getName().equals(role.getName())) {
            nameExists(param.getName());
        }
        return super.updateByPO(RoleVO.class, roleParam2PO(param, role, currentUser), currentUser);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private UserDomain userDomain;

    @Autowired private ResourceDomain resourceService;

    @Autowired private RoleRepository roleRepository;

    @Autowired private Transformer transformer;

    /**
     * Transform {@link RoleParam} to {@link Role}.
     *
     * @param param       {@link RoleParam}
     * @param role        {@link Role}
     * @param currentUser currentUser
     * @return {@link Role}
     */
    private Role roleParam2PO(RoleParam param, Role role, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, role, currentUser);
        if (!StringUtils.isBlank(param.getUserIds())) {
            List<User> users = userDomain.getAllByIds(transformer.idsStr2List(param.getUserIds()));
            role.setUsers(transformer.list2Set(users));
        }
        if (!StringUtils.isBlank(param.getResourceIds())) {
            List<Resource> resources = resourceService.getAllByIds(transformer.idsStr2List(param.getResourceIds()));
            role.setResources((Set<Resource>) resources);
        }
        return role;
    }

    private void nameExists(String name) throws Exception {
        if (roleRepository.findByNameAndValidFlag(name, ValidFlag.VALID).isPresent()) {
            // Throw role already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
        }
    }

}
