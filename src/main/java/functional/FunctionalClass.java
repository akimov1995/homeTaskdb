package functional;

import org.apache.log4j.Logger;
import utils.JdbcUtils;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class FunctionalClass {
    private static Logger logger = Logger.getLogger(FunctionalClass.class);

    public static Map<String,Integer> selectQuery(){
        Map<String, Integer> map = new HashMap<>();
        try(Connection connection = JdbcUtils.getConnection()){
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT a.name, COUNT(alb.artist_id) " +
                    "FROM artists AS a LEFT JOIN albums AS alb ON a.artist_id = " +
                    "alb.artist_id GROUP BY a.artist_id having count(alb.artist_id) > 2;");

            resultSet.next();
            String name = resultSet.getString(1);
            int count = resultSet.getInt(2);
            map.put(name,count);
            logger.info(MessageFormat.format("Выполнен запрос к таблицам artists, albums." +
                    " Результат artist name = {0}, album count = {1}",name,count));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return map;
    }

    public static Map<String,String> selectQuery2(){
        Map<String, String> map = new HashMap<>();
        try(Connection connection = JdbcUtils.getConnection()){
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("select a.name as album_name," +
                    " t.name as track_name from albums a right join tracks as " +
                    "t ON t.album_id = a.album_id where t.album_id is null ;");

            resultSet.next();
            String albumName = resultSet.getString(1);
            String trackName = resultSet.getString(2);
            map.put(albumName,trackName);
            logger.info(MessageFormat.format("Выполнен запрос к таблицам albums, tracks." +
                    " Результат album name = {0}, track name = {1}",albumName,trackName));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return map;
    }


    public static String callProcedure(){
        String callString = "{ ? = call get_max_copies_count_name() }";
        String result = null;
        try(Connection connection = JdbcUtils.getConnection()){
            CallableStatement callableStatement = connection.prepareCall(callString);
            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.execute();
            result = callableStatement.getString(1);
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        logger.info(MessageFormat.format("Вызвана хранимая процедура  get_max_copies_count_name()" +
                "результат name = {0}",result));
        return result;
    }

    public static void createTransaction(){
        try(Connection connection = JdbcUtils.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("UPDATE albums SET copies_count = copies_count + 1000 " +
                    "where artist_id = 1 and name = 'mmlp';");
            statement.execute("UPDATE artists SET salary = salary + 200000 where artist_id = 1;");
            connection.commit();
            //connection.setAutoCommit(true);
            logger.info("Выполнена транзакция : 1) у альбома с name = mmlp исполителя Eminem увеличено поле " +
                    "copies_count на 1000 2)у исполнителя с name = Eminem увеличено поле salary на 200000");
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }
}
