package com.hy2yang.demo.entity;

public class User{  
    private Integer id;  
    private String userName;  
    private String password;  
    private Integer age;  
    private String trueName;  
    private String email;  
    private String phone;  
    private String roleName;  
    
    public Integer getId() {  
        return id;  
    }  
    public void setId(Integer id) {  
        this.id = id;  
    }  
  
    public Integer getAge() {  
        return age;  
    }  
    public void setAge(Integer age) {  
        this.age = age;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public String getUserName() {  
        return userName;  
    }  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
    public String getTrueName() {  
        return trueName;  
    }  
    public void setTrueName(String trueName) {  
        this.trueName = trueName;  
    }  
    public String getEmail() {  
        return email;  
    }  
    public void setEmail(String email) {  
        this.email = email;  
    }  
    public String getPhone() {  
        return phone;  
    }  
    public void setPhone(String phone) {  
        this.phone = phone;  
    }  
    public String getRoleName() {  
        return roleName;  
    }  
    public void setRoleName(String roleName) {
        this.roleName = roleName;  
    }  
}
