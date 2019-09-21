package functional;

import utils.JdbcUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FunctionalClass {
    public static Map<String,Integer> selectQuery(){
        Connection connection = JdbcUtils.getConnection();
        Map<String, Integer> map = new HashMap<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT a.name, COUNT(alb.artist_id) FROM artists AS a LEFT JOIN\n" +
                    "albums AS alb ON a.artist_id = alb.artist_id GROUP BY a.artist_id having count(alb.artist_id) > 2;");

            resultSet.next();
            String name = resultSet.getString(1);
            int count = resultSet.getInt(2);
            map.put(name,count);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        return map;
    }

    public static Map<String,String> selectQuery2(){
        Connection connection = JdbcUtils.getConnection();
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select a.name as album_name," +
                    " t.name as track_name from albums a right join tracks as " +
                    "t ON t.album_id = a.album_id where t.album_id is null ;");

            resultSet.next();
            String albumName = resultSet.getString(1);
            String trackName = resultSet.getString(2);
            map.put(albumName,trackName);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        return map;
    }


    public static String callProcedure(){
        Connection connection = JdbcUtils.getConnection();
        String callString = "{ ? = call get_max_copies_count() }";
        String result = null;
        try {
            CallableStatement callableStatement = connection.prepareCall(callString);
            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.execute();
            result = callableStatement.getString(1);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        return result;
    }

    public static void createTransaction(){
        Connection connection = JdbcUtils.getConnection();
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("UPDATE albums SET copies_count = copies_count + 1000 where artist_id = 1 and name = 'mmlp';");
            statement.execute("UPDATE artists SET salary = salary + 200000 where artist_id = 1;");
            connection.commit();
            //connection.setAutoCommit(true);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
