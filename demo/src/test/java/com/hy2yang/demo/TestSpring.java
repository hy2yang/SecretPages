package com.hy2yang.demo;

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
        r.setMessage("test message");
        r.setIsURL(false);
        r.setGroup(4);
        us.add(r);
    }  
}  
