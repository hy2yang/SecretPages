package com.hy2yang.demo.util;

public class StringUtil {  
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
    * 格式化模糊查询 
    * @param str 
    * @return 
    */  
   public static String formatLike(String str){  
       if(!isEmpty(str)){  
           return "%"+str+"%";  
       }else{  
           return null;  
       }  
   }  
}  