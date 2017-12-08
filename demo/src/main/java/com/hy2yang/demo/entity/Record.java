package com.hy2yang.demo.entity;

import java.io.Serializable;

public class Record implements Serializable{
    
    
    private static final long serialVersionUID = -6665358364803420865L;
    
    private Integer id;      
    private String message;
    private boolean isURL;
    private int group;
    
    
    public boolean isURL() {
        return isURL;
    }
    public void setIsURL(boolean isURL) {
        this.isURL = isURL;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }
    public Integer getId() {  
        return id;  
    }  
    public void setId(Integer id) {  
        this.id = id;  
    }  
  
   
}
