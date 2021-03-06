package main.dataBaseHelper;

import java.sql.*;

public class dataBaseConVars {
    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet dBResult = null;
    public static String host = "localhost";
    public static String dataBaseName = "new_schema";
    public static String username = "root";
    public static String pass = null;
    public static int ALREADY_EXIST = 202;
    public static int OK = 1;
    public static int NOT_FOUNDED = 404;
    public static String TIME_TEST = "00:00:00";
    public static String DATE_TEST = "12-12-12";
    /**
     * start database connection
     */
    public static void startConnection(){
        try {
            String url = String.format("jdbc:mysql://%s/%s",host,dataBaseName);
            conn = DriverManager.getConnection(url,username,pass);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("connection error " + ex );
        }
    }
    /**
     * close database connection
     */
    public static void close(){
        if (dBResult != null) {
            try {
                dBResult.close();
            } catch (SQLException sqlEx) { } // ignore
            dBResult = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore
            stmt = null;
        }
    }
}
