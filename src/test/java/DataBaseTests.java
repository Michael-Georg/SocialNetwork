import Dao.ConnectionPool;
import Dao.FriendsDao;
import Dao.PersonDao;
import models.Person;
import org.junit.BeforeClass;
import org.junit.Test;
import servlets.listeners.Init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DataBaseTests {
    private static ConnectionPool pool;
    private static PersonDao personDao;
    private static FriendsDao friendsDao;

    @BeforeClass
    public static void init() throws Exception {
        pool = ConnectionPool.create("src\\main\\resources\\db.properties");
        Init init = new Init();
        init.initDb(pool, "src\\main\\resources\\init.sql");
        personDao = new PersonDao(pool);
        friendsDao = new FriendsDao(pool);
    }

    @Test
    public void simpleSelect() throws Exception {
        Person.PersonBuilder builder = Person.builder();
        try (Connection con = pool.get();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, first_name, last_name FROM Person")) {
            while (rs.next()) {
                System.out.println(builder.id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .build());
            }
        }
    }

    @Test
    public void addPersonWithEmptySlotsAndTakeIt() throws Exception {
        String email = "Alex@Alex.com";
        personDao.create(Person.builder()
                .firstName("Alex")
                .lastName("AlexA")
                .email(email)
                .password("1")
                .build());
        Person person = personDao.getByEmail("Alex@Alex.com").get();
        assertThat(person.getEmail(), is(equalTo(email)));
    }

    @Test
    public void printAllFriendsPair() throws Exception {
        try(Connection con = pool.get();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Friends")){
            while (rs.next())
                System.out.println(rs.getInt("id_user") + " + " + rs.getInt("id_friend"));
        }
    }

    @Test
    public void printAllFriends() throws Exception {
        System.out.println(friendsDao.getAll(1).size());

    }
}
