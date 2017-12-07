package com.hy2yang.demo.util;

public class PageBean {  
    private int cur; // current page number  
    private int pageSize; 
    
    public PageBean(int page, int pageSize) {  
        super();  
        this.cur = page;  
        this.pageSize = pageSize;  
    }  
    public int getPage() {  
        return cur;  
    }  
    public void setPage(int page) {  
        this.cur = page;  
    }  
    public int getPageSize() {  
        return pageSize;  
    }  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
    public int getStartIndex() {  
        return (cur-1)*pageSize;  
    }  
}  