package Dao;

import lombok.AllArgsConstructor;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class PersonDao implements DaoFactory<Person,Long>{
    public static final String SQL = "SELECT first_name, last_name FROM Person WHERE id = ?";
    private ConnectionPool connectionPool;

    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public Person getEntity(Long id) {
        try(Connection con = connectionPool.get();
            PreparedStatement prepStat = con.prepareStatement(SQL)){
            prepStat.setInt(1, id.intValue());
            try(ResultSet rs = prepStat.executeQuery()){
                if (rs.next()){
                    return Person.builder()
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
}
