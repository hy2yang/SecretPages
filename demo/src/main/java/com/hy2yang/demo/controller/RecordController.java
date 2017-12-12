package com.hy2yang.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hy2yang.demo.entity.Record;
import com.hy2yang.demo.service.RecordService;
import com.hy2yang.demo.util.*;

@Controller  
@RequestMapping("/records")  
public class RecordController {  
    
    private static Logger log=LoggerFactory.getLogger(RecordController.class);  
    @Resource  
    private RecordService recordService;  
      
    @RequestMapping("/showUser.do")  
    public String toIndex(HttpServletRequest request,Model model){ 
        return "showUser";  
    }  
    // /user/test.do?id=1  
    @RequestMapping(value="/test.do",method=RequestMethod.GET)    
    public String test(HttpServletRequest request,Model model){    
        int recordId = Integer.parseInt(request.getParameter("id"));    
        System.out.println("recordId:"+recordId);  
        Record r=null;  
        if (recordId==1) {  
             r = new Record(); 
             r.setId(1);
        }  
        log.debug(r.toString());  
        model.addAttribute("user", r);    
        return "index";    
    }    
    // /user/showUser.do?id=1  
    @RequestMapping(value="/showUser.do",method=RequestMethod.GET)    
    public String toindex(HttpServletRequest request,Model model){    
        int recordId = Integer.parseInt(request.getParameter("id"));    
        System.out.println("recordId:"+recordId);  
        Record r = this.recordService.getRecordById(recordId);    
        log.debug(r.toString());  
        model.addAttribute("user", r);    
        return "showUser";    
    }    
      
 // /user/showUser2.do?id=1  
    @RequestMapping(value="/showUser2.do",method=RequestMethod.GET)    
    public String toIndex2(@RequestParam("id") String id,Model model){    
        int recordId = Integer.parseInt(id);    
        System.out.println("recordId:"+recordId);  
        Record r = this.recordService.getRecordById(recordId);    
        log.debug(r.toString());  
        model.addAttribute("user", r);    
        return "showUser";    
    }    
  
    // /user/jsontype.do?id=1  
    @RequestMapping(value="/jsontype.do",method=RequestMethod.GET)    
    public @ResponseBody Record getUserInJson(@RequestParam("id") String id,Map<String, Object> model){    
        int recordId = Integer.parseInt(id);    
        System.out.println("recordId:"+recordId);  
        Record r = this.recordService.getRecordById(recordId);    
        log.info(r.toString());  
        return r;    
    }    
    // /user/jsontype2.do?id=1  
    @RequestMapping(value="/jsontype2.do",method=RequestMethod.GET)    
    public ResponseEntity<Record>  getUserInJson2(@RequestParam("id") String id,Map<String, Object> model){    
        int recordId = Integer.parseInt(id);    
        System.out.println("recordId:"+recordId);  
        Record r = this.recordService.getRecordById(recordId);    
        log.info(r.toString());  
        return new ResponseEntity<Record>(r,HttpStatus.OK);    
    }   
      
    
    /** 
     * 用户管理页面 
     * @return 
     */  
    @RequestMapping(value="/userManage.do")  
    public String userManagePage(){  
        return "userManage";  
    } 
    
    
    /** 
     * adding or updating 
     * @param r 
     * @param res 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/save.do")  
    public void save (Record r, String tbk, HttpServletResponse res) throws Exception{  
        System.out.println(tbk);
        System.out.println(r);
        
        int resultTotal = 0;  // records updated
        
        Map<String,Object> map= FormatUtil.GetRecordInputMap(tbk,r);
        
        if (r.getId() == null) {  
            resultTotal = recordService.add(map);  
        }else{  
            resultTotal = recordService.update(r);  
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
        
        //System.out.println(page);
        //System.out.println(rows);
        
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
        try{
            recordList=recordService.find(map);
        } catch(BadSqlGrammarException e) {
            recordList=new ArrayList<Record>();
            System.out.println("no record is using current key");
        }
        Long total=recordService.getTotal(map);
        
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
        return;  
    }  
}  
