package dao;

import model.Album;
import model.Track;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDao {
    public void addTrack(Track track) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tracks" +
                    "(name, producer_name, album_id) VALUES(?,?,?);");

            preparedStatement.setString(1,track.getName());
            preparedStatement.setString(2,track.getProducerName());
            preparedStatement.setInt(3,track.getAlbumId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    public void updateTrack(Track track) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tracks SET " +
                    "name = ?, producer_name = ?, album_id = ? WHERE track_id = ?;");

            preparedStatement.setString(1, track.getName());
            preparedStatement.setString(2, track.getProducerName());
            preparedStatement.setInt(3, track.getAlbumId());
            preparedStatement.setInt(4, track.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }



    public void deleteTrack(int id) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM tracks WHERE track_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public Track getTrackById(int id) {
        Connection connection = JdbcUtils.getConnection();
        Track track = null;
        try {
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
            System.out.println(ex.getMessage());
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return track;
    }
}
