package com.hy2yang.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hy2yang.demo.entity.Record;
import com.hy2yang.demo.service.RecordService;
import com.hy2yang.demo.util.*;

@Controller  
@RequestMapping("/records")  
public class RecordController {  
    
    @Resource  
    private RecordService recordService;  
    
    /** 
     * adding or updating 
     * @param r 
     * @param res 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/save.do")  
    public void save (Record r, String tbk, HttpServletResponse res) throws Exception{ 
        
        int resultTotal = 0;  // records updated
        
        Map<String,Object> map= FormatUtil.GetRecordInputMap(tbk,r);
        
        if (r.getId() == null) {  
            resultTotal = recordService.add(map);  
        }else{  
            resultTotal = recordService.update(map);  
        }  
        
        ObjectMapper mapper = new ObjectMapper(); 
        ObjectNode result=mapper.createObjectNode();
        
        if(resultTotal > 0){   
            result.put("success", Boolean.TRUE);  
        }else{  
            result.put("success", Boolean.FALSE);  
        }  
        
        ResponseUtil.write(res, result);  
        return;  
    }
    
    /** 
     * show page of records using certain key 
     * @param page 
     * @param rows 
     * @param r 
     * @param tableKey 
     * @param res
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/list.do")  
    public void list(@RequestParam(value="page",required=true) String page,@RequestParam(value="rows",required=true)String rows,
            @RequestParam(value="tableKey",required=true) String tableKey, HttpServletResponse res) throws Exception{
        
        ObjectMapper mapper = new ObjectMapper(); 
        if (tableKey==null || tableKey.length()<1) {
            ResponseUtil.write(res, mapper.writeValueAsString(""));
            return;
        }        
        
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));  
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("tableKey", tableKey);
        map.put("start", pageBean.getStartIndex());  
        map.put("size", pageBean.getPageSize());
        
        List<Record> recordList;
        Long total;
        try{
            recordList=recordService.find(map);
            total=recordService.getTotal(tableKey);
        } catch(BadSqlGrammarException e) {
            recordList=new ArrayList<Record>();
            total=(long) 0;
            System.out.println("no record is using current key");
        }
        
        Map<String,Object> result=new HashMap<String,Object>();
        
        result.put("total", total);
        result.put("rows", recordList);         
        ResponseUtil.write(res, mapper.writeValueAsString(result));        
        return;  
    }  
    
    /** 
     * record deletion
     * @param ids 
     * @param res 
     * @param tableKey
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/delete.do")  
    public void delete(@RequestParam(value="ids",required=true) String ids, @RequestParam(value="tableKey",required=true) String tableKey,
            HttpServletResponse res) throws Exception{  
        String[] idStr = ids.split(",");         
        ObjectMapper mapper = new ObjectMapper(); 
        ObjectNode result=mapper.createObjectNode();
        
        for (String id : idStr) {  
            recordService.delete(tableKey, Integer.parseInt(id));  
        } 
        
        result.put("success", Boolean.TRUE);
        ResponseUtil.write(res, result); 
        
        if (recordService.getTotal(tableKey)<1) {
            recordService.dropEmpty(tableKey);
        }

        return;  
    }  
}  
