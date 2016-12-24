package test.cc.superliar.repo;

import cc.superliar.Application;
import cc.superliar.po.Manage;

import cc.superliar.po.ManagePK;
import cc.superliar.repo.ManageReposity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by shentao on 2016/12/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class test {


    @Autowired
    private ManageReposity manageReposity;

    @Test
    public void likeName() {

        ManagePK id = new ManagePK(1,"111");
//        Manage manage = manageReposity.findById(id).orElse(null);
//        System.out.println(manage);
        Manage manage = new Manage(1,"111");
        manageReposity.save(manage);
        System.out.println(manage);

    }

}
