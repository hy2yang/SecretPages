package com.hy2yang.demo.service;
import java.util.List;
import java.util.Map;

import com.hy2yang.demo.entity.Record;


public interface RecordService {
    public List<Record> getAll();  
    public Record getRecord(Record r);  
    public int delete(String tableKey, int id);  
    public int update(Record r);  
    public int add(Record r);  
    public List<Record> find(Map<String,Object> map);  
    public Long getTotal(Map<String,Object> map);  
    public Record getRecordById(int id);  
}
