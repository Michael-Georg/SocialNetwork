package Dao;

import Dao.common.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import models.Person;
import models.Relation;
import models.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
public class RelationDao {
    private ConnectionPool connectionPool;

    public RelationDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(Relation relation){
        String SQL2 = "INSERT INTO Relation (id_user, id_friend, status) VALUES (?, ?, ?)";
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL2)){
            ps.setInt(1, relation.getUser_id());
            ps.setInt(2, relation.getFriend_id());
            ps.setInt(3, relation.getStatus().getType());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e);
        }

    }

    public void update(Relation relation){
        String SQL = "UPDATE Relation SET status = ? WHERE id_friend = ? AND id_user = ?";
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL)){
            ps.setInt(1, relation.getStatus().getType());
            ps.setInt(2, relation.getFriend_id());
            ps.setInt(3, relation.getUser_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e);
        }

    }

    public void remove(Relation relation){
        String SQL1 = "DELETE FROM Relation WHERE id_user = ? AND id_friend =?";
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL1)){
            ps.setInt(1, relation.getUser_id());
            ps.setInt(2, relation.getFriend_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("not remove relation", e);
        }
    }


    public int getRelation(int userId, int friendId){
        String SQL3 = "SELECT * FROM Relation WHERE id_user = ? AND id_friend =?";
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(SQL3)){
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? rs.getInt("status") : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> followersList(int userId){
        String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
                "(SELECT id_user FROM Relation WHERE id_friend = ? AND status = ?)";
        return getAll(userId, Status.FOLLOW, SQL);
    }

    public List<Person> ignoreList(int userId){

        String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
                "(SELECT id_user FROM Relation WHERE id_friend = ? AND status = ?)";

        return getAll(userId, Status.BLOCK, SQL);
    }

    public List<Person> followingList(int userId) {
        String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
                "(SELECT id_friend FROM Relation WHERE id_user = ? AND status = ?)";

        return getAll(userId, Status.FOLLOW, SQL);

    }


    private List<Person> getAll(int userId, Status status, String sql){
        List<Person> result = new LinkedList<>();
        try(Connection con = connectionPool.get();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, userId);
            ps.setInt(2, status.getType());
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next())
                    result.add(readPerson(rs).get());
                return result;
            }
        } catch (SQLException e) {
            log.error("getAll - ", e);
        }
        return null;
    }

    private Optional<Person> readPerson(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Person.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .id(rs.getInt("id"))
                .build());
    }
}
