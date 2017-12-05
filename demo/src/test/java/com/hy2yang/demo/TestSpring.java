package com.hy2yang.demo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hy2yang.demo.entity.User;
import com.hy2yang.demo.service.UserService;

public class TestSpring {  
    @Test  
    public void TestUserService() throws Exception{  
        @SuppressWarnings("resource")  
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");  
        UserService us=(UserService) ac.getBean("userService");  
        User user=new User();  
        user.setAge(18);  
        user.setUserName("Rose");  
        user.setPassword("goeson");  
        user.setTrueName("MsRose");  
        us.add(user); 
        us.delete(2);
    }  
}  
