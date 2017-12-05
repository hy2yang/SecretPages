package com.hy2yang.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hy2yang.demo.entity.User;
import com.hy2yang.demo.serviceImpl.UserServiceImpl;

@Controller  
@RequestMapping("/test")
public class LoginController {
    
    @Autowired  
    private UserServiceImpl userService;
    
    @RequestMapping("/dologin.do") //url  
    public String dologin(User user, Model model){  
        if(userService.doUserLogin(user)){  
            model.addAttribute("successMsg", "Log in success!");  
            model.addAttribute("name", user.getUsername());  
            return "/success";  
        }else{  
            model.addAttribute("failMsg", "wrong user or password!");  
            return "/fail";  
        }     
    }  

}
