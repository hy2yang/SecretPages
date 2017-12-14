package com.hy2yang.demo.service;
import java.util.List;
import java.util.Map;

import com.hy2yang.demo.entity.Record;


public interface RecordService {
    public List<Record> getAll();  
    public Record getRecord(Record r);  
    public int delete(String tableKey, int id); 
    public int dropEmpty(String tableKey);
    public int update(Map<String,Object> map);  
    public int add(Map<String,Object> map);  
    public List<Record> find(Map<String,Object> map);  
    public Long getTotal(String tableKey);  
    public Record getRecordById(int id);  
}
