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

    public FriendsDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void addFriend(int myId, int friendId){

    }

    public void removeFriend(int friendId){

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
