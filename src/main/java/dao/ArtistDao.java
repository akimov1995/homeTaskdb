package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import model.Artist;
import org.apache.log4j.Logger;
import utils.JdbcUtils;

public class ArtistDao {
    private static Logger logger = Logger.getLogger(ArtistDao.class);

    public void addArtist(Artist artist) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO artists " +
                    "(name,salary,label_name) VALUES (?,?,?);");

            preparedStatement.setString(1,artist.getName());
            preparedStatement.setInt(2,artist.getSalary());
            preparedStatement.setString(3,artist.getLabelName());
            preparedStatement.executeUpdate();
            logger.info(MessageFormat.format("В таблицу artists добавлен исполнитель = {0}",artist));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    public void updateArtist(Artist artist) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE artists SET " +
                    "name = ?, salary = ?, label_name = ? " +
                    "WHERE artist_id = ?;");

            preparedStatement.setString(1,artist.getName());
            preparedStatement.setInt(2,artist.getSalary());
            preparedStatement.setString(3,artist.getLabelName());
            preparedStatement.setInt(4,artist.getArtistId());
            preparedStatement.executeUpdate();

            logger.info(MessageFormat.format("В таблице artists изменён исполнитель с id = {0}," +
                    " теперь artist = {1}",artist.getArtistId(),artist));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }



    public void deleteArtist(int id) throws SQLException{
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM artists WHERE artist_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info(MessageFormat.format("Из таблицы artists удалён исполнитель с id = {0}",id));
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    public Artist getArtistById(int id) {
        Artist artist = null;
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM artists WHERE artist_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                artist = new Artist();
                artist.setArtistId(resultSet.getInt("artist_id"));
                artist.setName(resultSet.getString("name"));
                artist.setSalary(resultSet.getInt("salary"));
                artist.setLabelName(resultSet.getString("label_name"));
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        logger.info(MessageFormat.format("Из таблицы artists выбран " +
                "исполнитель с id = {0}, artist = {1}",id,artist));
        return artist;
    }
}
