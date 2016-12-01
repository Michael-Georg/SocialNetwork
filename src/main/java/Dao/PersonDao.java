package Dao;

import Dao.common.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import models.Person;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
public class PersonDao implements Dao<Person, Integer> {
    private static final String SQL = "SELECT id, first_name, last_name, email, dob, address, telephone, info FROM Person WHERE id = ?";
    private static final String SQL1 = "SELECT id, first_name, last_name, email, password, dob, address, telephone, info FROM Person WHERE email = ?";
    private static final String SQL2 = "INSERT INTO Person (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    private static final String SQL3 = "UPDATE Person SET first_name = ?, last_name = ?, dob = ?, address = ?, telephone = ?, info = ?, password = ? WHERE email = ?";
    private static final String SQL4 = "SELECT * FROM Person";
    private ConnectionPool connectionPool;

    public PersonDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Person> getAll() {
        List<Person> result = new LinkedList<>();
        try (Connection con = connectionPool.get();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL4)) {
            while (rs.next()) {
                result.add(readPerson(rs).orElse(null));
            }
        } catch (SQLException e) {
            log.error("get all error");        }
        return result;
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
                if (rs.next() && rs.getString("password").equals(pass))
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
        try (Connection con = connectionPool.get();
             PreparedStatement ps = con.prepareStatement(SQL3)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setDate(3, Optional.ofNullable(entity.getDob()).map(Date::valueOf).orElse(null));
            ps.setString(4, entity.getAddress());
            ps.setString(5, entity.getTelephone());
            ps.setString(6, entity.getInfo());
            ps.setString(7, entity.getPassword());
            ps.setString(8, entity.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public void delete(Integer id) {

    }

    private Optional<Person> readPerson(ResultSet rs) throws SQLException {
        return Optional.ofNullable(Person.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .dob(Optional.ofNullable(rs.getDate("dob"))
                        .map(Date::toLocalDate).orElse(null))
                .address(rs.getString("address"))
                .telephone(rs.getString("telephone"))
                .id(rs.getInt("id"))
                .info(rs.getString("info"))
                .build());

    }
}