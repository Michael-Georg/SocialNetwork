package Dao;

import Dao.common.ConnectionPool;
import models.Message;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MessageDao {
    private static final String SQL = "SELECT id, id_user, text FROM Messages WHERE id_user = ? ORDER BY id ASC";
    private String SQL1 = "DELETE FROM Messages WHERE id = ?";
    private String SQL2 = "INSERT INTO Messages (id_user, text) VALUES (?, ?)";
    private ConnectionPool connectionPool;

    public MessageDao(ConnectionPool pool) {
        connectionPool = pool;
    }

    public List<Message> getAll(int myId) {
        List<Message> result = new LinkedList<>();
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, myId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    result.add(readMessage(rs).get());
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Message message) {
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL2)){
            ps.setInt(1, message.getUser_id());
            ps.setString(2, message.getText());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                .build());
    }
}
