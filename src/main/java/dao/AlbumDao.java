package dao;

import model.Album;
import model.Artist;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumDao {

    public void addAlbum(Album album) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO albums(name, genre, " +
                    "copies_count, artist_id) VALUES (?,?,?,?);");

            preparedStatement.setString(1,album.getName());
            preparedStatement.setString(2,album.getGenre());
            preparedStatement.setInt(3,album.getCopiesCount());
            preparedStatement.setInt(4,album.getArtistId());
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
    public void updateAlbum(Album album) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE albums SET " +
                    "name = ?, genre = ?, copies_count = ?, artist_id = ? WHERE album_id = ?;");

            preparedStatement.setString(1, album.getName());
            preparedStatement.setString(2, album.getGenre());
            preparedStatement.setInt(3, album.getCopiesCount());
            preparedStatement.setInt(4, album.getArtistId());
            preparedStatement.setInt(5, album.getId());
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



    public void deleteAlbum(int id) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM albums WHERE album_id = ?");
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

        public Album getAlbumById(int id) {
        Connection connection = JdbcUtils.getConnection();
        Album album = null;
        try {
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
        return album;
    }
}
