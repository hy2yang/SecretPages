package com.hy2yang.demo.util;

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
    
    
   /*static boolean isEmpty(String str){  
       if(str==null||"".equals(str.trim())){  
           return true;  
       }else{  
           return false;  
       }  
   }*/
   
   /**
    *  wrap and format tableKey and record into a map for mybatis
    *  @param String tableKey
    *  @param Record record
    */   
   public static HashMap<String,Object> GetRecordInputMap(String tbk, Record r) {
       HashMap<String,Object> res =new HashMap<>();
       res.put("tableKey", tbk);
       res.put("r_id", r.getId());
       res.put("r_message", encrypt(r.getMessage(),tbk));
       res.put("r_URL", r.isURL());
       res.put("r_group", encrypt(r.getGroup(),tbk));
       return res;
   }
   
   public static String encrypt(String text, String pw) {
        byte[] key = pw.getBytes();
        SecretKeySpec ks = new SecretKeySpec(key, "Blowfish");
        Cipher cipher;
        byte[] encrypted;
        
        try {
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, ks);
            encrypted = cipher.doFinal(text.getBytes(StandardCharsets.ISO_8859_1));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            encrypted = null;
            e.printStackTrace();
        }
        
        String res = new String(encrypted, StandardCharsets.ISO_8859_1);
        //System.out.println(res);
        return res;
   }
   
   public static String decrypt(String text, String pw) {
        byte[] key = pw.getBytes();
        SecretKeySpec ks = new SecretKeySpec(key, "Blowfish");
        Cipher cipher;

        byte[] encrypted = text.getBytes(StandardCharsets.ISO_8859_1);
        byte[] decrypted;
        
        try {
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, ks);
            decrypted = cipher.doFinal(encrypted);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            decrypted = null;
            e.printStackTrace();
        }
        
        String res = new String(decrypted, StandardCharsets.ISO_8859_1);
        //System.out.println(res);
        return res;
   }
   
   public static Record encryptRecord(Record r, String key) {  
       Record res=new Record();
       res.setId(r.getId());
       res.setURL(r.isURL());
       res.setMessage(encrypt(r.getMessage(),key));
       res.setGroup(encrypt(r.getGroup(),key));
       return res;
   }
   
   
   public static Record decryptRecord(Record r, String key) {  
       Record res=new Record();
       res.setId(r.getId());
       res.setURL(r.isURL());
       res.setMessage(decrypt(r.getMessage(),key));
       res.setGroup(decrypt(r.getGroup(),key));
       return res;
   }
   
   
   public static void main(String[] args) {
       String t=System.getProperty("user.name");
       System.out.println(t);
       String en=encrypt(t,"records");
       decrypt(en,"records");
   }
 
}  