package cc.superliar.controller;

/**
 * Created by shentao on 2016/11/9.
 */

import cc.superliar.annotation.CurrentUser;
import cc.superliar.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 前端页面显示的控制器
 * 共包括archives,login,projects,tags,about,post,login这几个页面
 */
@Controller
public class FontEndCtrl {


    /**
     * 到登录页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login");
        return mav;
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView index(@CurrentUser User currentUser) {
        String username = currentUser.getName();
        ModelAndView mav = new ModelAndView();
        mav.addObject("username",username);
        mav.setViewName("/index");
        return mav;
    }


//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public ModelAndView logout(@CurrentUser UserManageVO currentUser) {
//        String username = currentUser.getName();
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("username",username);
//        mav.setViewName("/login");
//        return mav;
//    }

}
