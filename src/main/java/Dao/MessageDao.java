package Dao;

import Dao.common.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import models.Message;
import models.WSMessage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Data Access Object for work with {@link models.Message}.
 * Include methods to create, remove and some methods to read entity.
 */

@Slf4j
public class MessageDao {
    private static final String SQL = "SELECT id, id_user, id_post, text, publish_time FROM Messages WHERE id_user = ? AND id_post = -1";
    private static final String SQL3 = "SELECT id, id_user, id_post, text, publish_time FROM Messages WHERE id_post = ?";
    private static final String SQL1 = "DELETE FROM Messages WHERE id = ?";
    private static final String SQL2 = "INSERT INTO Messages (id_user, id_post, text, publish_time) VALUES (?, ?, ?, ?)";
    private ConnectionPool connectionPool;

    /**
     * Initialize instance of this class with given pool of open connections to DB
     *
     * @param pool create  in {@link servlets.listeners.Init}
     */
    public MessageDao(ConnectionPool pool) {
        connectionPool = pool;
    }

    /**
     * Retrieves {@link Message} from database with id_post = -1.
     *
     * @param userId ID of user who create the messages
     * @return Set(Message) with Messages (Posts)
     */
    public Set<Message> getAllPosts(int userId) {
        Set<Message> result = new TreeSet<>((o1, o2) ->
                o1.getId() > o2.getId() ? 1 : (o1.getId() == o2.getId() ? 0 : -1));
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    result.add(readMessage(rs));
            }
        } catch (SQLException e) {
            log.error("Failed searching Messages", e);
            return Collections.emptySet();
        }
        return result;
    }

    /**
     * Retrieves {@link Message}(Comments) from database with postId.
     *
     * @param postId ID of message(Post) which comments searching
     * @return Set(Message) with Messages(Comments)
     */
    public Set<Message> getAllComments(int postId) {
        Set<Message> result = new TreeSet<>((o1, o2) ->
                o1.getId() > o2.getId() ? 1 : (o1.getId() == o2.getId() ? 0 : -1));
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL3)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    result.add(readMessage(rs));
            }
        } catch (SQLException e) {
            log.error("Failed searching Messages", e);
            return Collections.emptySet();
        }
        return result;
    }

    /**
     * Create new {@link Message} in database.
     *
     * @param message {@link WSMessage} JSON message model for WebSocket chanel
     * @return Message added to database
     */
    public Message add(WSMessage message) {
        LocalDateTime time = LocalDateTime.now();
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, message.getUser_id());
            ps.setInt(2, message.getPost_id());
            ps.setString(3, message.getText());
            ps.setTimestamp(4, Timestamp.valueOf(time));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    return Message.builder()
                            .id(rs.getInt(1))
                            .post_id(message.getPost_id())
                            .text(message.getText())
                            .user_id(message.getUser_id())
                            .time(time)
                            .build();
            }
        } catch (SQLException e) {
            log.error("Failed addition to database", e);
        }
        return null;
    }

    /**
     * Delete from database Message with ID equal to ID of given one
     * it was assigned. Also deletes it from cache.
     *
     * @param msgId id message to delete
     */
    @SuppressWarnings("unused")
    public void remove(int msgId) {
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL1)) {
            ps.setInt(1, msgId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed deleting Message", e);
        }
    }

    /**
     * Read one note from ResultSet and create new Message
     * Return Optional<Person> with Person or with Optional.empty()
     *
     * @param rs note with Message data
     * @return {@link Message}
     * @throws SQLException if can't read note
     */
    private Message readMessage(ResultSet rs) throws SQLException {
        return Message.builder()
                .text(rs.getString("text"))
                .id(rs.getInt("id"))
                .user_id(rs.getInt("id_user"))
                .post_id(rs.getInt("id_post"))
                .time(Optional.ofNullable(rs.getTimestamp("publish_time"))
                        .map(Timestamp::toLocalDateTime).orElse(null))
                .build();
    }
}
