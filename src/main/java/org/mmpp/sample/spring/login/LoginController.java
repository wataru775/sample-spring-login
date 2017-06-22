package org.mmpp.sample.spring.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("login");
        LoginBean loginBean = new LoginBean();
        model.addObject("loginBean", loginBean);
        return model;
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute LoginBean loginBean){
        ModelAndView model= null;
        try{
            boolean isValidUser = isValidUser(loginBean.username, loginBean.password);
            if(isValidUser){
                System.out.println("User Login Successful");
                request.setAttribute("loggedInUser", loginBean.username);
                model = new ModelAndView("welcome");
            }else{
                model = new ModelAndView("login");
                model.addObject("loginBean", loginBean);
                request.setAttribute("message", "Invalid credentials!!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return model;
    }
    public boolean isValidUser(String username, String password) {
        return (username.equals("a") && password.equals("a"));
    }

}
