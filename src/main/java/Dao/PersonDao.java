package Dao;

import lombok.AllArgsConstructor;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PersonDao implements Dao<Person, Long> {
    private static final String SQL1 = "SELECT first_name, last_name, password FROM Person WHERE email = ?";
    private static final String SQL = "SELECT first_name, last_name FROM Person WHERE id = ?";
    private ConnectionPool connectionPool;

    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public Optional<Person> getEntity(Long id) {
        try (Connection con = connectionPool.get();
             PreparedStatement prepStat = con.prepareStatement(SQL)) {
            prepStat.setInt(1, id.intValue());
            try (ResultSet rs = prepStat.executeQuery()) {
                return rs.next() ? readPerson(rs) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Person> isRegistered(String login, String password) {
        try (Connection con = connectionPool.get();
             PreparedStatement prepStat = con.prepareStatement(SQL1)) {
            prepStat.setString(1, login);
            try (ResultSet rs = prepStat.executeQuery()) {
                if (rs.next())
                    if (password.equals(rs.getString("password")))
                        return readPerson(rs);
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Person entity) {
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void create(Person entity) {

    }

    public Optional<Person> readPerson(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Person.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build());

    }
}