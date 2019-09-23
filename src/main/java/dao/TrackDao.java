package dao;

import model.Album;
import model.Track;
import org.apache.log4j.Logger;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class TrackDao {
    private static Logger logger = Logger.getLogger(ArtistDao.class);

    public void addTrack(Track track) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tracks" +
                    "(name, producer_name, album_id) VALUES(?,?,?);");

            preparedStatement.setString(1,track.getName());
            preparedStatement.setString(2,track.getProducerName());
            preparedStatement.setInt(3,track.getAlbumId());
            preparedStatement.executeUpdate();
            logger.info(MessageFormat.format("В таблицу tracks добавлен трек = {0}",track));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    public void updateTrack(Track track) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tracks SET " +
                    "name = ?, producer_name = ?, album_id = ? WHERE track_id = ?;");

            preparedStatement.setString(1, track.getName());
            preparedStatement.setString(2, track.getProducerName());
            preparedStatement.setInt(3, track.getAlbumId());
            preparedStatement.setInt(4, track.getId());
            preparedStatement.executeUpdate();

            logger.info(MessageFormat.format("В таблице tracks изменён трек с id = {0}," +
                    " теперь track = {1}",track.getId(),track));
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    public void deleteTrack(int id) {
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM tracks WHERE track_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info(MessageFormat.format("Из таблицы tracks удалён трек с id = {0}",id));
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Track getTrackById(int id) {
        Track track = null;
        try(Connection connection = JdbcUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM tracks WHERE track_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                track = new Track();
                track.setId(resultSet.getInt("track_id"));
                track.setName(resultSet.getString("name"));
                track.setProducerName(resultSet.getString("producer_name"));
                track.setAlbumId(resultSet.getInt("album_id"));
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        logger.info(MessageFormat.format("Из таблицы tracks выбран " +
                "трек с id = {0}, track = {1}",id,track));
        return track;
    }
}
