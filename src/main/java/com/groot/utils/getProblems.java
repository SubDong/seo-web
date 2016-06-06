package com.groot.utils;

import java.util.*;

/**
* Created by SubDong on 2015/2/12.
*/
public class getProblems {
    public static Map<String,String> readProperties(String filePath) {
        Map<String,String> stringMap = new HashMap<>();

        ResourceBundle rb = ResourceBundle.getBundle(filePath, Locale.getDefault());
        for (String s : rb.keySet()) {
            String value = rb.getString(s);
            stringMap.put(s,value);
        }

        return stringMap;
    }
}
