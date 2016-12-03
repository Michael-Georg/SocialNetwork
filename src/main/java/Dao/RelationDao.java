package Dao;

import Dao.common.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import models.Person;
import models.Relation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for work with {@link models.Relation}.
 * Include methods to create, remove and some methods to read entities.
 */

@Slf4j
public class RelationDao {
    private ConnectionPool connectionPool;

    /**
     * Initialize instance of this class with given pool of open connections to DB
     *
     * @param connectionPool create  in {@link servlets.listeners.Init}
     */
    public RelationDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Create new {@link Relation} in database.
     *
     * @param relation {@link Relation}
     */
    public void add(Relation relation) {
        String SQL2 = "INSERT INTO Relation (id_user, id_friend, status) VALUES (?, ?, ?)";
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL2)) {
            ps.setInt(1, relation.getUser_id());
            ps.setInt(2, relation.getFriend_id());
            ps.setInt(3, relation.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed creating Relation", e);
        }

    }

    /**
     * Update {@link Relation} status in database.
     *
     * @param relation {@link Relation}
     */
    public void update(Relation relation) {
        String SQL = "UPDATE Relation SET status = ? WHERE id_friend = ? AND id_user = ?";
        String SQL1 = "UPDATE Relation SET status = ? WHERE id_user = ? AND id_friend = ?";
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(relation.getStatus() == 1 ? SQL1 : SQL)) {
            ps.setInt(1, relation.getStatus());
            ps.setInt(2, relation.getFriend_id());
            ps.setInt(3, relation.getUser_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed updating", e);
        }

    }

    /**
     * Remove {@link Relation} from database.
     *
     * @param relation {@link Relation}
     */
    public void remove(Relation relation) {
        String SQL1 = "DELETE FROM Relation WHERE id_user = ? AND id_friend =?";
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL1)) {
            ps.setInt(1, relation.getUser_id());
            ps.setInt(2, relation.getFriend_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed removing Relation", e);
        }
    }

    /**
     * @param userId   Id of authorized user
     * @param friendId Another user id
     * @return {@link Relation} status from database.
     */
    public int getRelation(int userId, int friendId) {
        String SQL3 = "SELECT * FROM Relation WHERE id_user = ? AND id_friend =?";
        if (userId == friendId)
            return 2;
        else {
            try (Connection con = connectionPool.get();
                 PreparedStatement ps = con.prepareStatement(SQL3)) {
                ps.setInt(1, userId);
                ps.setInt(2, friendId);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? rs.getInt("status") : 0;
                }
            } catch (SQLException e) {
                log.error("Failed searching Relation", e);
                return 0;
            }
        }
    }

    /**
     * @param userId authorized user id
     * @return  List<Person> {@link Person} which follow to authorized user.
     */
    public List<Person> followersList(int userId) {
        String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
                "(SELECT id_user FROM Relation WHERE id_friend = ? AND status = ?)";
        return getAll(userId, 2, SQL);
    }

    /**
     * @param userId authorized user id
     * @return  List<Person> {@link Person} which blocked by authorized user.
     */
    public List<Person> ignoreList(int userId) {

        String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
                "(SELECT id_user FROM Relation WHERE id_friend = ? AND status = ?)";

        return getAll(userId, 1, SQL);
    }

    /**
     * @param userId authorized user id
     * @return  List<Person> {@link Person} which following by authorized user.
     */
    public List<Person> followingList(int userId) {
        String SQL = "SELECT first_name, last_name, id FROM Person WHERE id IN " +
                "(SELECT id_friend FROM Relation WHERE id_user = ? AND status = ?)";

        return getAll(userId, 2, SQL);

    }


    private List<Person> getAll(int userId, int status, String sql) {
        List<Person> result = new LinkedList<>();
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    result.add(readPerson(rs).orElse(null));
                return result;
            }
        } catch (SQLException e) {
            log.error("Failed list creation", e);
            return Collections.emptyList();
        }
    }

    /**
     * Read one note from ResultSet and create new Message
     * Return Optional<Person> with Person or with Optional.empty()
     * @param rs note with Person data
     * @return Optional<Person> {@link Person}
     * @throws SQLException if can't read note
     */
    private Optional<Person> readPerson(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Person.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .id(rs.getInt("id"))
                .build());
    }
}