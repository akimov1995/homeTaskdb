package utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.sql.*;


public class JdbcUtils {
    private static Connection connection;
    private static String userName = "postgres";
    private static String password = "1234";
    private static String url = "jdbc:postgresql://localhost:5432/";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        connection = null;
        try {
            String dataBaseUrl = url + "hometask_db";
            connection = DriverManager.getConnection(dataBaseUrl,userName,password);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void createDataBase(){
        connection = null;
        try {
            connection = DriverManager.getConnection(url, userName,password);
            connection.createStatement().execute("CREATE DATABASE hometask_db;");
            connection.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void dropDataBase(){
        connection = null;
        try {
            connection = DriverManager.getConnection(url, userName,password);
            connection.createStatement().execute("drop database if exists hometask_db;");
            connection.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void createTables(){
        connection = null;
        try {
            String dataBaseUrl = url + "hometask_db";
            connection = DriverManager.getConnection(dataBaseUrl,userName,password);
            File file = new File("src/main/resources/scripts/createTables.sql");
            String sqlQuery = FileUtils.readFileToString(file,"utf-8");
            connection.createStatement().execute(sqlQuery);
            File file2 = new File("src/main/resources/scripts/createFunctions.sql");
            String sqlQuery2 = FileUtils.readFileToString(file2,"utf-8");
            connection.createStatement().execute(sqlQuery2);
            connection.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
