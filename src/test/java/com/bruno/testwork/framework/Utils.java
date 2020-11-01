package com.bruno.testwork.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static String getProperty(String key) {
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro.getProperty(key);
    }

}
