package com.groot.utils;

import com.groot.db.hadoop.HadoopPool;

import java.io.File;
import java.util.Calendar;

/**
 * Created by caoyi on 2014-12-30.
 */
public class HDFSUtils {
    /**
     * 文件按 /sites/{date}/src/{site domain name}/{page path} 的路径存取
     *
     * @return
     */
    public static String getHdfsPathPrefix() {
        StringBuilder result = new StringBuilder();

        Calendar calendar = Calendar.getInstance();

        result.append("hdfs://")
                .append(HadoopPool.getNamenode())
                .append(":")
                .append(HadoopPool.getPort())
                .append(File.separator)
                .append(calendar.get(Calendar.YEAR))
                .append(File.separator)
                .append(String.format("%02d", calendar.get(Calendar.MONTH) + 1))
                .append(File.separator)
                .append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)))
                .append(File.separator)
                .append("src")
                .append(File.separator);
        return result.toString();
    }

}
