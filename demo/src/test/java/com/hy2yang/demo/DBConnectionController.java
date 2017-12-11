package com.hy2yang.demo;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

//@Controller
//@RequestMapping("/index") 
public class DBConnectionController {
    
    //@Resource
    private DBConfig config;
    
    //@RequestMapping("/connect.do")  
    public ModelAndView tryConnect(HttpServletRequest request, @RequestParam(value="url",required=true) String url,
            @RequestParam(value="user",required=true)String user, @RequestParam(value="password",required=true)String password){
        
        ModelAndView mav = new ModelAndView();
        
        if (connectSuccess(url, user, password)) {
            mav.addObject("alert", "database connected");
            config.setProperty("jdbc.url", url);
            config.setProperty("jdbc.username", user);
            config.setProperty("jdbc.password", password);            
            mav.setViewName("Records");
        } else {
            mav.addObject("alert", "fail to connect to database");
            mav.setViewName("index");
        }
        
        return mav;
    }
    
    private boolean connectSuccess(String url, String user, String password) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;            
        }        
        return true;
    }

}
