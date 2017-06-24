package org.mmpp.sample.spring.login.controller;

import org.mmpp.sample.spring.login.SessionForm;
import org.mmpp.sample.spring.login.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.w3c.dom.Entity;

@Controller
@SessionAttributes(types = { SessionForm.class })
public class IndexContoller {
    private final Logger logger = LoggerFactory.getLogger(IndexContoller.class);

    @Autowired
    private UsersService userService;

    @RequestMapping(value={"/index","/"},method= RequestMethod.GET)
    public String index(SessionForm form){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if(name.equals("anonymousUser"))return "redirect:index";
        if(form.getUser() == null){
            logger.info("== get Name ==");
            form.setUser(userService.findByUsername(name));
        }
        logger.info(form.getUser().getName());
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        logger.info("[details]");
        logger.info("  Session ID : " + details.getSessionId());

        UserDetails principal = (UserDetails) auth.getPrincipal();
        logger.info("[principal]");
        logger.info("  username : " + principal.getUsername());
        logger.info("  password : " + principal.getPassword());

        return "index";
    }
}
