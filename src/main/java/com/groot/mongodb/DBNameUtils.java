package com.groot.mongodb;

/**
 * Created by SubDong on 2015/2/2.
 */
public class DBNameUtils {
    public static final String DB_REPORT = "report";

    /**
     * @param args
     * @return
     */
    public static String getDatabaseName(String... args) {
        String databaseName = null;

        if (args.length == 0) {
            databaseName = "sys";
        } else if (args.length == 1) {
            //只传用户名
            databaseName = "user_" + args[0];   //如: user_perfect
        } else if (args.length == 2) {
            databaseName = "user_" + args[0] + "_" + args[1];   //如: user_perfect_report
        }

        return databaseName;
    }


    public static String getSysDBName() {
        return "sys";
    }

    public static String getUserDBName(String userName, String dbType) {
        if (userName != null && dbType != null) {
            //只传用户名
            return "user_" + userName + "_" + dbType;     //如: user_perfect_report

        }

        if (userName != null) {
            return "user_" + userName; //如: user_perfect
        }

        return null;
    }


    public static String getReportDBName(String userName) {
        return getUserDBName(userName, DB_REPORT);
    }
}
