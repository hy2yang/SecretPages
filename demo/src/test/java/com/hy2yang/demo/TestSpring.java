package com.hy2yang.demo;

import java.util.Random;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hy2yang.demo.entity.Record;
import com.hy2yang.demo.service.RecordService;

public class TestSpring {  
    @Test  
    public void TestRecordService() throws Exception{  
        @SuppressWarnings("resource")  
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");  
        RecordService us=(RecordService) ac.getBean("recordService");  
        Record r=new Record();  
        r.setGroup("test group0");
        r.setURL(false);
        
        for (int i=0;i<30;++i) {
            r.setMessage(randomString(8));
            us.add(r);
        }
    }  
    
    static final String AN = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    private String randomString( int len ){
       StringBuilder sb = new StringBuilder( len );
       for( int i = 0; i < len; i++ ) 
          sb.append( AN.charAt( rnd.nextInt(AN.length()) ) );
       return sb.toString();
    }
}  
