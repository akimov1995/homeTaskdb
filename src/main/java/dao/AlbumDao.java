package dao;

import model.Album;
import model.Artist;
import org.apache.log4j.Logger;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class AlbumDao {
    private static Logger logger = Logger.getLogger(AlbumDao.class);

    public void addAlbum(Album album) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO albums(name, genre, " +
                    "copies_count, artist_id) VALUES (?,?,?,?);");

            preparedStatement.setString(1,album.getName());
            preparedStatement.setString(2,album.getGenre());
            preparedStatement.setInt(3,album.getCopiesCount());
            preparedStatement.setInt(4,album.getArtistId());
            preparedStatement.executeUpdate();

            logger.info(MessageFormat.format("В таблицу albums добавлен альбом = {0}",album));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    public void updateAlbum(Album album) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE albums SET " +
                    "name = ?, genre = ?, copies_count = ?, artist_id = ? WHERE album_id = ?;");

            preparedStatement.setString(1, album.getName());
            preparedStatement.setString(2, album.getGenre());
            preparedStatement.setInt(3, album.getCopiesCount());
            preparedStatement.setInt(4, album.getArtistId());
            preparedStatement.setInt(5, album.getId());
            preparedStatement.executeUpdate();

            logger.info(MessageFormat.format("В таблице albums изменён альбом с id = {0}," +
                    " теперь album = {1}",album.getId(),album));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    public void deleteAlbum(int id) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM albums WHERE album_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info(MessageFormat.format("Из таблицы albums удалён альбом с id = {0}",id));
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Album getAlbumById(int id) {
        Album album = null;
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM albums WHERE album_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                album = new Album();
                album.setId(resultSet.getInt("album_id"));
                album.setName(resultSet.getString("name"));
                album.setGenre(resultSet.getString("genre"));
                album.setCopiesCount(resultSet.getInt("copies_count"));
                album.setArtistId(resultSet.getInt("artist_id"));
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        logger.info(MessageFormat.format("Из таблицы albums выбран альбом с id = {0},album = {1}",id,album));
        return album;
    }
}
