package Dao;

import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class FriendsDao {
    private ConnectionPool connectionPool;
    private String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
            "(SELECT id_friend FROM Friends WHERE id_user = ?)";
    private String SQL1 = "DELETE FROM Friends WHERE id_user = ? AND id_friend =?";
    private String SQL2 = "INSERT INTO Friends (id_user, id_friend) VALUES (?, ?)";
    private String SQL3 = "SELECT * FROM Friends WHERE id_user = ? AND id_friend =?";

    public FriendsDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(int userId, int friendId){
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL2)){
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void remove(int userId, int friendId){
        try(Connection con = connectionPool.get();
        PreparedStatement ps = con.prepareStatement(SQL1)){
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int friendStatus(int userId, int friendId){
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL3)){
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? 1 : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAll(int myId){
        List<Person> result = new LinkedList<>();
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, myId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next())
                    result.add(readPerson(rs).get());
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Optional<Person> readPerson(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Person.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .id(rs.getInt("id"))
                .build());
    }

}
