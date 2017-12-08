package com.hy2yang.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy2yang.demo.entity.Record;
import com.hy2yang.demo.service.RecordService;
import com.hy2yang.demo.util.PageBean;
import com.hy2yang.demo.util.ResponseUtil;
import com.hy2yang.demo.util.StringUtil;

@Controller  
@RequestMapping("/user")  
public class RecordController {  
    
    private static Logger log=LoggerFactory.getLogger(RecordController.class);  
    @Resource  
    private RecordService userService;  
      
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
        Record r = this.userService.getRecordById(recordId);    
        log.debug(r.toString());  
        model.addAttribute("user", r);    
        return "showUser";    
    }    
      
 // /user/showUser2.do?id=1  
    @RequestMapping(value="/showUser2.do",method=RequestMethod.GET)    
    public String toIndex2(@RequestParam("id") String id,Model model){    
        int recordId = Integer.parseInt(id);    
        System.out.println("recordId:"+recordId);  
        Record r = this.userService.getRecordById(recordId);    
        log.debug(r.toString());  
        model.addAttribute("user", r);    
        return "showUser";    
    }    
  
    // /user/jsontype.do?id=1  
    @RequestMapping(value="/jsontype.do",method=RequestMethod.GET)    
    public @ResponseBody Record getUserInJson(@RequestParam("id") String id,Map<String, Object> model){    
        int recordId = Integer.parseInt(id);    
        System.out.println("recordId:"+recordId);  
        Record r = this.userService.getRecordById(recordId);    
        log.info(r.toString());  
        return r;    
    }    
    // /user/jsontype2.do?id=1  
    @RequestMapping(value="/jsontype2.do",method=RequestMethod.GET)    
    public ResponseEntity<Record>  getUserInJson2(@RequestParam("id") String id,Map<String, Object> model){    
        int recordId = Integer.parseInt(id);    
        System.out.println("recordId:"+recordId);  
        Record r = this.userService.getRecordById(recordId);    
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
     * 添加或者修改 
     * @param r 
     * @param res 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/save.do")  
    public void save(Record r,HttpServletResponse res) throws Exception{  
        //操作记录条数，初始化为0  
        int resultTotal = 0;  
        if (r.getId() == null) {  
            resultTotal = userService.add(r);  
        }else{  
            resultTotal = userService.update(r);  
        }  
        JSONObject jsonObject = new JSONObject();  
        if(resultTotal > 0){   //说明修改或添加成功  
            jsonObject.put("success", true);  
        }else{  
            jsonObject.put("success", false);  
        }  
        ResponseUtil.write(res, jsonObject);  
        return;  
    }  
    /** 
     * 用户分页查询 
     * @param page 
     * @param rows 
     * @param r 
     * @param res 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/list.do")  
    public void list(@RequestParam(value="page",required=false) String page,@RequestParam(value="rows",required=false) 
    String rows,Record r,HttpServletResponse res) throws Exception{  
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));  
        Map<String,Object> map=new HashMap<String,Object>();  
        map.put("userName", StringUtil.formatLike(r.getMessage()));  
        map.put("start", pageBean.getStartIndex());  
        map.put("size", pageBean.getPageSize());  
        List<Record> userList=userService.find(map);  
        Long total=userService.getTotal(map);  
        JSONObject result=new JSONObject();  
        JSONArray jsonArray=JSONArray.fromObject(userList);  
        result.put("rows", jsonArray);  
        result.put("total", total);  
        ResponseUtil.write(res, result);  
        return;  
    }  
    /** 
     * 删除用户 
     * @param ids 
     * @param res 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping("/delete.do")  
    public void delete(@RequestParam(value="ids") String ids,HttpServletResponse res) throws Exception{  
        String[] idStr = ids.split(",");  
        JSONObject jsonObject = new JSONObject();  
        for (String id : idStr) {  
            userService.delete(Integer.parseInt(id));  
        }  
        jsonObject.put("success", true);  
        ResponseUtil.write(res, jsonObject);  
        return;  
    }  
}  