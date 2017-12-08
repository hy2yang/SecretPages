package com.hy2yang.demo.serviceImpl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hy2yang.demo.dao.RecordDao;
import com.hy2yang.demo.entity.Record;
import com.hy2yang.demo.service.RecordService;

@Service(value="recordService")  
public class RecordServiceImpl implements RecordService {  
    private RecordDao recordDao;  
    
    @Resource  
    public void setUserDao(RecordDao recordDao) {  
        this.recordDao = recordDao;  
    }  
    @Override  
    public List<Record> getAll() {  
        return recordDao.getAll();  
    }  
  
    @Override  
    public Record getRecord(Record r) {  
           return recordDao.getRecord(r);  
    }  
  
    @Override  
    public int delete(int id) {  
        return recordDao.delete(id);  
    }  
  
    @Override  
    public int update(Record r) {  
         return recordDao.update(r);  
    }  
  
    @Override  
    public int add(Record r) {  
          return recordDao.add(r);  
    }  
  
    @Override  
    public List<Record> find(Map<String, Object> map) {  
         return recordDao.find(map);  
    }  
  
    @Override  
    public Long getTotal(Map<String, Object> map) {  
         return recordDao.getTotal(map);  
    }  
    public Record getRecordById(int id){  
        return recordDao.getRecordById(id);  
    }
    
}  