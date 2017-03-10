package cc.superliar.controller;

/**
 * Created by shentao on 2016/11/9.
 */

import cc.superliar.annotation.CurrentUser;
import cc.superliar.domain.UserDomain;
import cc.superliar.exception.CommonsException;
import cc.superliar.param.UserParam;
import cc.superliar.po.User;
import cc.superliar.vo.UserVO;
import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * 到注册页面
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/register");
        return mav;
    }


    /**
     * 到主界面
     * @param currentUser
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView index(@CurrentUser User currentUser) {
        String username = currentUser.getName();
        ModelAndView mav = new ModelAndView();
        mav.addObject("username",username);
        mav.setViewName("/index");
        return mav;
    }


    /**
     * 检测账户
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity changepwd(HttpServletRequest request) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String userAcount=request.getParameter("account");
        User user = userDomain.findByUsr(userAcount);
        if(user!=null){
            map.put("flag",true);
        }else {
            map.put("flag",false);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);


    }


    /**
     * 检测账户
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(HttpServletRequest request,@Valid UserParam param) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();

        UserVO userVO = userDomain.create(param);
        if(userVO!=null){
            return new ResponseEntity<>(userVO, HttpStatus.OK);
        }else{
            map.put("code",201);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }



    @Autowired
    private UserDomain userDomain;
}
