package cc.superliar.repo;

import cc.superliar.po.Device;
import cc.superliar.po.Manage;

import cc.superliar.vo.ManageVO;
import cc.superliar.vo.UrlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sun.util.resources.cldr.ga.LocaleNames_ga;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

}
