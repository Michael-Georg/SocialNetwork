package Dao;

import Dao.common.ConnectionPool;
import models.Message;
import models.WSMessage;

import java.sql.*;
import java.util.*;

public class MessageDao {
    private static final String SQL = "SELECT id, id_user, id_post, text FROM Messages WHERE id_user = ? AND id_post = -1";
    private static final String SQL3 = "SELECT id, id_user, id_post, text FROM Messages WHERE id_post = ?";
    private String SQL1 = "DELETE FROM Messages WHERE id = ?";
    private String SQL2 = "INSERT INTO Messages (id_user, id_post, text) VALUES (?, ?, ?)";
    private ConnectionPool connectionPool;

    public MessageDao(ConnectionPool pool) {
        connectionPool = pool;
    }

    public Set<Message> getAllPosts(int userId) {
        Set<Message> result = new TreeSet<>((o1, o2) -> o1.getId() - o2.getId());
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    result.add(readMessage(rs).get());
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Message> getAllComments(int postId) {
//        Set<Message> result = new TreeSet<>((o1, o2) -> o1.getId() - o2.getId());
        List<Message> result = new LinkedList<>();
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL3)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    result.add(readMessage(rs).get());
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message add(WSMessage message) {
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, message.getUser_id());
            ps.setInt(2, message.getPost_id());
            ps.setString(3, message.getText());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    return Message.builder()
                            .id(rs.getInt(1))
                            .post_id(message.getPost_id())
                            .text(message.getText())
                            .user_id(message.getUser_id())
                            .build();
                else throw new RuntimeException("No message id ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int msgId) {
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL1)) {
            ps.setInt(1, msgId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Optional<Message> readMessage(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Message.builder()
                .text(rs.getString("text"))
                .id(rs.getInt("id"))
                .user_id(rs.getInt("id_user"))
                .post_id(rs.getInt("id_post"))
                .build());
    }
}
