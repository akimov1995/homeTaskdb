package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Artist;
import utils.JdbcUtils;

public class ArtistDao {

    public void addArtist(Artist artist) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO artists " +
                    "(name,salary,label_name) VALUES (?,?,?);");

            preparedStatement.setString(1,artist.getName());
            preparedStatement.setInt(2,artist.getSalary());
            preparedStatement.setString(3,artist.getLabelName());
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
    public void updateArtist(Artist artist) {
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE artists SET " +
                    "name = ?, salary = ?, label_name = ? " +
                    "WHERE artist_id = ?;");

            preparedStatement.setString(1,artist.getName());
            preparedStatement.setInt(2,artist.getSalary());
            preparedStatement.setString(3,artist.getLabelName());
            preparedStatement.setInt(4,artist.getArtistId());
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



    public void deleteArtist(int id) throws SQLException{
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("DELETE FROM artists WHERE artist_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            throw ex;
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

    public Artist getArtistById(int id) {
        Connection connection = JdbcUtils.getConnection();
        Artist artist = null;
        try {
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
        return artist;
    }
}
