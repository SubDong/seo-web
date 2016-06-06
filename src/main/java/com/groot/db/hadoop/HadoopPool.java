package com.groot.db.hadoop;

import java.util.ResourceBundle;

/**
 * Created by caoyi on 2014-12-19.
 */
public class HadoopPool {
    private static String namenode;
    private static String port;

    private HadoopPool(){
    }

    static  {
        ResourceBundle bundle = ResourceBundle.getBundle("hadoop");
        namenode = bundle.getString("hadoop.namenode");
        port = bundle.getString("hadoop.port");
    }

    public static String getNamenode() {
        return namenode;
    }

    public static String getPort() {
        return port;
    }

}