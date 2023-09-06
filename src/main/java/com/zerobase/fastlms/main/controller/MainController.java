package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    
    @RequestMapping("/")
    public String index() {
        

//        String email = "test.huny.jhkim@gmail.com";
//        String subject = " 안녕하세요. 운영자입니다. ";
//        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";
//
//        mailComponents.sendMail(email, subject, text);

        
        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
}
