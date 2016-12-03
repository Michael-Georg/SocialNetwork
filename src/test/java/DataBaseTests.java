import Dao.MessageDao;
import Dao.PersonDao;
import Dao.RelationDao;
import Dao.common.ConnectionPool;
import models.Person;
import models.WSMessage;
import org.junit.BeforeClass;
import org.junit.Test;
import servlets.listeners.Init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DataBaseTests {
    private static ConnectionPool pool;
    private static PersonDao personDao;
    private static RelationDao relationDao;
    private static MessageDao messageDao;

    @BeforeClass
    public static void init() throws Exception {
        pool = ConnectionPool.create("src\\main\\resources\\db.properties");
        Init init = new Init();
        init.initDb(pool, "src\\main\\resources\\init.sql");
        personDao = new PersonDao(pool);
        relationDao = new RelationDao(pool);
        messageDao = new MessageDao(pool);
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
        Person person = personDao.getByEmail("Alex@Alex.com").orElse(null);
        assertThat(person.getEmail(), is(equalTo(email)));
    }

    @Test
    public void printAllFriendsPair() throws Exception {
        try (Connection con = pool.get();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Relation")) {
            while (rs.next())
                System.out.println(rs.getInt("id_user") + " + " + rs.getInt("id_friend"));
        }
    }

    @Test
    public void updateTest() throws Exception {
        personDao.create(Person.builder()
                .firstName("Mike")
                .lastName("Mike")
                .email("Mike@Mike.ru")
                .password("123")
                .build());

        Person person = Person.builder()
                .firstName("Mi")
                .lastName("Mi")
                .email("Mike@Mike.ru")
                .dob(LocalDate.parse("2000-05-10"))
                .password("111")
                .build();
        personDao.update(person);

        System.out.println(personDao.getByEmail("Mike@Mike.ru"));
    }

    @Test
    public void printAllFriends() throws Exception {
        System.out.println("FOLLOWing" + relationDao.followingList(1));
        System.out.println("BLOCK" + relationDao.ignoreList(1));
        System.out.println("followers" + relationDao.followersList(1));
    }


    @Test
    public void msgGetAll() throws Exception {
        WSMessage msg = WSMessage.builder()
                .post_id(1)
                .text("for post 1")
                .user_id(1)
                .build();
        messageDao.add(msg);
        System.out.println(messageDao.getAllComments(1));
    }
}
