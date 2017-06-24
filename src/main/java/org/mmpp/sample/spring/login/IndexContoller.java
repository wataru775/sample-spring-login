package org.mmpp.sample.spring.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexContoller {
    private final Logger logger = LoggerFactory.getLogger(IndexContoller.class);


    @RequestMapping(value={"/index","/"},method= RequestMethod.GET)
    public String index(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if(name.equals("anonymousUser"))return "redirect:index";
        logger.info(name);

        return "index";
    }
}
