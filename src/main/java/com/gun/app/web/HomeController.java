package com.gun.app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * 기본 컨트롤러
 */
@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "/index";
    }

    @GetMapping("/user")
    public String user(Principal principal){
        log.info("user name :: "+principal.getName());
        return "/user";
    }
}