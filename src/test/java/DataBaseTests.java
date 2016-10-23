import Dao.ConnectionPool;
import models.Person;
import org.junit.Test;
import servlets.listeners.Init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseTests {
    @Test
    public void dbInit() throws Exception {
        ConnectionPool pool = ConnectionPool.create("src\\main\\resources\\db.properties");
        Init init = new Init();
        init.initDb(pool, "src\\main\\resources\\init.sql");
        Person.PersonBuilder builder = Person.builder();
        try(Connection con = pool.get();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, first_name, last_name FROM Person")){
            while(rs.next()) {
                System.out.println(builder.id(rs.getLong("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .build());
            }

        }
    }
}
