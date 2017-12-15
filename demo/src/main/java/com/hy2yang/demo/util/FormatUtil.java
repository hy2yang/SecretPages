package com.hy2yang.demo.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
   
   public static String encrypt(String pw, String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
       byte[] key=pw.getBytes();       
       SecretKeySpec ks = new SecretKeySpec(key, "Blowfish");
       Cipher cipher;
       
       cipher = Cipher.getInstance("Blowfish");
       cipher.init(Cipher.ENCRYPT_MODE, ks);
       
       byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.ISO_8859_1));       
       String res= new String(encrypted, StandardCharsets.ISO_8859_1);
       System.out.println(res);
       return res;
   }
   
   public static String decrypt(String pw, String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
       byte[] key=pw.getBytes();       
       SecretKeySpec ks = new SecretKeySpec(key, "Blowfish");
       Cipher cipher;
       
       byte[] encrypted = text.getBytes(StandardCharsets.ISO_8859_1);
       
       cipher = Cipher.getInstance("Blowfish");
       cipher.init(Cipher.DECRYPT_MODE, ks);
       
       byte[] decrypted = cipher.doFinal(encrypted);
       String res = new String(decrypted, StandardCharsets.ISO_8859_1);
       System.out.println(res);
       return res;
   }
   
   public static void main(String[] args) {
       String t=System.getProperty("user.name");
       System.out.println(t);
       String en=" ";
        try {
            en = encrypt("testpw",t);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            decrypt("testpw",en);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
   }
 
}  