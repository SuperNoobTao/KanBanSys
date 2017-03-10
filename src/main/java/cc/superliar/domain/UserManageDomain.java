package cc.superliar.domain;


import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.component.Transformer;
import cc.superliar.param.UserManageParam;
import cc.superliar.po.User;
import cc.superliar.po.UserManage;
import cc.superliar.repo.UserManageReposity;
import cc.superliar.vo.UserManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shentao on 2017/3/9.
 */
@Service
@Transactional(readOnly = true)
public class UserManageDomain extends BaseDomain<UserManage,Integer>{


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * 批量删除对应关系
     * @param isList
     */
    @Transactional
    public void deleteUserManages(List<String> isList, User currentUser) throws Exception{
        for(String id : isList) {
            manageReposity.delete(Integer.valueOf(id));
        }
    }


    /**
     * 在删除user的时候级联把role都删除
     * @param id
     */
    @Transactional
    public void deleteByUserId(Long id){
        manageReposity.deleteManageById(id);
    }


    /**
     * 创建
     * @param param
     * @param currentUser
     * @return
     * @throws Exception
     */
    @Transactional
    public UserManageVO create(UserManageParam param, User currentUser) throws Exception {
        UserManage manage = new UserManage(param.getRole(),param.getUser());
        return super.createByPO(UserManageVO.class, manage, currentUser);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private UserManageReposity manageReposity;

    @Autowired private CustomPasswordEncoder passwordEncoder;

    @Autowired private Transformer transformer;
}
