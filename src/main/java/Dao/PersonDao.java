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
public class PersonDao implements Dao<Person, Integer> {
    private static final String SQL = "SELECT id, first_name, last_name, email FROM Person WHERE id = ?";
    private static final String SQL1 = "SELECT id, first_name, last_name, email, password FROM Person WHERE email = ?";
    private static final String SQL2 = "INSERT INTO Person (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    private ConnectionPool connectionPool;

    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public Optional<Person> getEntity(Integer id) {
        try (Connection con = connectionPool.get();
             PreparedStatement prepStat = con.prepareStatement(SQL)) {
            prepStat.setInt(1, id);
            try (ResultSet rs = prepStat.executeQuery()) {
                return rs.next() ? readPerson(rs) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Person> getByEmail(String email) {
        try (Connection con = connectionPool.get();
             PreparedStatement prepStat = con.prepareStatement(SQL1)) {
            prepStat.setString(1, email);
            try (ResultSet rs = prepStat.executeQuery()) {
                return rs.next() ? readPerson(rs) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Person> getByEmailAndPass(String email, String pass) {
        try (Connection con = connectionPool.get();
             PreparedStatement prepStat = con.prepareStatement(SQL1)) {
            prepStat.setString(1, email);
            try (ResultSet rs = prepStat.executeQuery()) {
                rs.next();
                if (rs.getString("password").equals(pass))
                    return readPerson(rs);
                else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(Person entity) {
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL2)) {
                ps.setString(1, entity.getFirstName());
                ps.setString(2, entity.getLastName());
                ps.setString(3, entity.getEmail());
                ps.setString(4, entity.getPassword());
                ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Person entity) {
    }

    @Override
    public void delete(Integer id) {

    }

    private Optional<Person> readPerson(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Person.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .id(rs.getInt("id"))
                .build());

    }
}