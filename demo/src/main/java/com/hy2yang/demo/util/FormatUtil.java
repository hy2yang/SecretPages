package com.hy2yang.demo.util;

import java.util.HashMap;

import com.hy2yang.demo.entity.Record;

public class FormatUtil {  
    /** 
    * if a string is empty 
    * @param str 
    * @return 
    */  
   public static boolean isEmpty(String str){  
       if(str==null||"".equals(str.trim())){  
           return true;  
       }else{  
           return false;  
       }  
   }
   
   /**
    *  wrap and format tableKey and record into a map for mybatis
    *  @param String tableKey
    *  @param Record record
    */   
   public static HashMap<String,Object> GetRecordInputMap(String tbk, Record r) {
       HashMap<String,Object> res =new HashMap<>();
       res.put("tableKey", "`"+tbk+"`");
       res.put("r_id", r.getId());
       res.put("r_message", r.getMessage());
       res.put("r_URL", r.isURL());
       res.put("r_group", r.getGroup());
       return res;
   }
 
}  