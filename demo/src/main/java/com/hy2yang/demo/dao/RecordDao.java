package com.hy2yang.demo.dao;

import com.hy2yang.demo.entity.Record;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.BadSqlGrammarException; 

public interface RecordDao {  
    
    /** 
     * 查询所有用户 
     * @return 
     */  
    public List<Record> getAll();  
    /** 
     * 根据条件查询用户 
     * @param user 
     * @return 
     */  
    public Record getRecord(Record p);  
    /** 
     * 删除用户 
     * @param user 
     * @return 
     */  
    public int delete(@Param("tableKey")String tableKey, @Param("id")int id);  
    /** 
     * 更新用户 
     * @param user 
     * @return 
     */  
    public int update(Record p);  
    /** 
     * 添加用户 
     * @param user 
     * @return 
     */  
    public int add(Map<String,Object> map);  
    /** 
     * 用户查询 
     * @param map 
     * @return 
     */  
    public List<Record> find(Map<String,Object> map) throws BadSqlGrammarException;  
    /** 
     * 获取总记录数 
     * @param map 
     * @return 
     */  
    public Long getTotal(Map<String,Object> map);  
    /** 
     * 根据id查询用户 
     * @param id 
     * @return 
     */  
    public Record getRecordById(int id);  
}  