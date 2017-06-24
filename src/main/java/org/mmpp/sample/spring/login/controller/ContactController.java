package org.mmpp.sample.spring.login.controller;

import org.mmpp.sample.spring.login.SessionForm;
import org.mmpp.sample.spring.login.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.w3c.dom.Entity;

@Controller
@SessionAttributes(types = { SessionForm.class, Entity.class })
public class ContactController {
    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private UsersService userService;

    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String index(SessionForm form) {
        if(form.getUser() == null){
            logger.info("== get Name ==");
            form.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        }
        logger.info(form.getUser().getName());
        return "contact";
    }
}