package com.hy2yang.demo;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;


public class DBConfig {
    
    
    private PropertiesConfiguration jdbcConfig;
    
    private DBConfig() {
        init("jdbc.properties");
    }

    /**
     * 
     *
     * @param propertiesFile
     * @see
     */
    private void init(String propertiesFile) {
        try {
            jdbcConfig = new PropertiesConfiguration(propertiesFile);            
            jdbcConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            jdbcConfig.setAutoSave(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param key
     * @return
     * @see
     */
    public Object getValue(String key) {
        return jdbcConfig.getProperty(key);
    }

    /**
     * 
     * @param key
     * @param value
     * @see
     */
    public void setProperty(String key, String value) {
        jdbcConfig.setProperty(key, value);
    }
    
    /*public static void main(String[] args) {
        DBConfig test=new DBConfig();        
        System.out.println(test.getValue("jdbc.url"));
        test.setProperty("jdbc.url", "jdbc:mysql://127.0.0.1:3306/demodb");
        System.out.println(test.getValue("jdbc.url"));
    }*/



}
