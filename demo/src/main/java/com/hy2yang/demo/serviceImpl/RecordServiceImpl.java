package com.hy2yang.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import com.hy2yang.demo.dao.RecordDao;
import com.hy2yang.demo.entity.Record;
import com.hy2yang.demo.service.RecordService;
import com.hy2yang.demo.util.FormatUtil;

@Service(value = "recordService")
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
    public int delete(String tableKey, int id) {
        return recordDao.delete(FormatUtil.encrypt(tableKey, System.getProperty("user.name")), id);
    }

    @Override
    public int update(Map<String, Object> map) {  
        String newkey=FormatUtil.encrypt((String) map.get("tableKey"), System.getProperty("user.name"));
        map.put("tableKey", newkey);
        return recordDao.update(map);
    }

    @Override
    public int add(Map<String, Object> map) {
        String newkey=FormatUtil.encrypt((String) map.get("tableKey"), System.getProperty("user.name"));
        map.put("tableKey", newkey);
        recordDao.newTable(newkey);
        return recordDao.add(map);
    }

    @Override
    public List<Record> find(Map<String, Object> map) throws BadSqlGrammarException {
        ArrayList<Record> res=new ArrayList<>();
        String key=(String) map.get("tableKey");
        String newkey=FormatUtil.encrypt((String) map.get("tableKey"), System.getProperty("user.name"));
        map.put("tableKey", newkey);
        for (Record r: recordDao.find(map)) {
            res.add(FormatUtil.decryptRecord(r,key));            
        }
        return res;
    }

    @Override
    public Long getTotal(String tableKey) throws BadSqlGrammarException {
        return recordDao.getTotal(FormatUtil.encrypt(tableKey, System.getProperty("user.name")));
    }

    public Record getRecordById(int id) {
        return recordDao.getRecordById(id);
    }

    @Override
    public int dropEmpty(String tableKey) {
        return recordDao.dropEmpty(FormatUtil.encrypt(tableKey, System.getProperty("user.name")));
    }

}