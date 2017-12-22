package com.hy2yang.demo.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Controller  
@RequestMapping("/index")
public class IndexController {            // simple function for demo, so no modularization
    
    @RequestMapping("/login.do")  
    public static String tryLogin(HttpServletRequest request, Model model, String url, String password, String user){
        url="jdbc:mysql://"+url;
        
        if (loginMySQL(url, user, password)) {
            saveProperties(url, user, password);
            return "/WEB-INF/jsp/Records"; 
        }        
        else {
            request.setAttribute("error", "connection failed!");
            return "/index";
        }
         
    }
    
    private static boolean loginMySQL(String url, String user, String password){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("fail to connect");
            return false;            
        }  
        System.out.println("connection success");
        return true;
    }
    
    private static void saveProperties(String url, String user, String password){
        Properties jdbcProp =new Properties();
        try {
            jdbcProp.load(new FileInputStream("src/main/resources/jdbc.properties"));
            jdbcProp.setProperty("jdbc.url", url);
            jdbcProp.setProperty("jdbc.username", user);
            jdbcProp.setProperty("jdbc.password", password);
            jdbcProp.store(new FileOutputStream("src/main/resources/jdbc.properties"),"");
        } catch (IOException e) {
            System.out.println("fail in loading or saving properties");
        }
    }
    
}
