package cc.superliar.repo;

import cc.superliar.po.Url;
import cc.superliar.vo.ManageVO;
import cc.superliar.vo.RoleVO;
import cc.superliar.vo.UrlVO;
import cc.superliar.vo.UserManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shentao on 2016/12/24.
 */
@Repository
public class NativeSQLReposity {

    @Autowired
    private EntityManager em;

    public List<ManageVO> listbydevice(String device){

        List<ManageVO> list = new ArrayList<>();
        String sql =
                "select id,device_id,m.url_id,u.url_content,u.url_description " +
                        "from tb_device_has_url m,tb_url u " +
                        "where m.url_id = u.url_id and  m.device_id=:device";
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setParameter("device", device);

        List objecArraytList = query.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
                ManageVO manageVO = new ManageVO();
                manageVO.setId((Integer) obj[0]);
                manageVO.setDevice((String) obj[1]);
                manageVO.setUrl((Integer) obj[2]);
                manageVO.setContent((String) obj[3]);
                manageVO.setDescription((String) obj[4]);
            list.add(manageVO);
        }
            return  list;
    }


    /**
     * 显示用户对应的角色
     * @param user
     * @return
     */
    public List<UserManageVO> listbyuser(Long user){

        List<UserManageVO> list = new ArrayList<>();
        String sql =
                "select id,user_id,m.role_id,u.role_name,u.role_description " +
                        "from tb_user_has_role m,tb_role u " +
                        "where m.role_id = u.role_id and  m.user_id=:userid";
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setParameter("userid", user);

        List objecArraytList = query.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserManageVO manageVO = new UserManageVO();
            manageVO.setId((Integer) obj[0]);
            manageVO.setUser((Integer) obj[1]);
            manageVO.setRole((Integer) obj[2]);
            manageVO.setName((String) obj[3]);
            manageVO.setDescription((String) obj[4]);
            list.add(manageVO);
        }
        return  list;
    }


    /**
     * 显示可添加的url
     * @param device
     * @return
     */
    public List<UrlVO> listbydevice2(String device){
        List<UrlVO> list = new ArrayList<>();
        String sql =
                "select url_id,url_content,url_description " +
                        "from tb_url u where url_valid_flag=1 and  u.url_id not in" +
                        " (select url_id from tb_device_has_url where device_id=:device )";
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setParameter("device", device);
        List objecArraytList = query.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UrlVO urlVO = new UrlVO();
            urlVO.setId(Long.valueOf((Integer) obj[0]));
            urlVO.setContent((String) obj[1]);
            urlVO.setDescription((String) obj[2]);
            list.add(urlVO);
        }
        return  list;

    }


    /**
     * 显示可添加的角色
     * @param user
     * @return
     */
    public List<RoleVO> listbyuser2(Long user){
        List<RoleVO> list = new ArrayList<>();
        String sql =
                "select role_id,role_name,role_description " +
                        "from tb_role u where role_valid_flag=1 and  u.role_id not in" +
                        " (select role_id from tb_user_has_role where user_id=:userid )";
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setParameter("userid", user);
        List objecArraytList = query.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            RoleVO roleVO = new RoleVO();
            roleVO.setId(Long.valueOf((Integer) obj[0]));
            roleVO.setName((String) obj[1]);
            roleVO.setDescription((String) obj[2]);
            list.add(roleVO);
        }
        return  list;

    }


    /**
     * 发送给android app的信息
     * @param device
     * @return
     */
    public List<Url> listbydevice3(String device){
        List<Url> list = new ArrayList<>();
        String sql =
                "select u.url_id,u.url_content,u.url_description " +
                        "from tb_device_has_url m,tb_url u " +
                        "where m.url_id = u.url_id and  m.device_id=:device";
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setParameter("device", device);

        List objecArraytList = query.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            Url url = new Url();
            url.setId(Long.valueOf((Integer) obj[0]));
            url.setContent((String) obj[1]);
            url.setDescription((String) obj[2]);
            list.add(url);
        }
        return  list;
    }




}
