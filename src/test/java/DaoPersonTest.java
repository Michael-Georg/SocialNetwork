import Dao.PersonDao;
import Dao.common.ConnectionPool;
import models.Person;
import org.junit.BeforeClass;
import org.junit.Test;
import servlets.listeners.Init;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class DaoPersonTest {
    private static PersonDao personDao;

    @BeforeClass
    public static void init() throws Exception {
        ConnectionPool pool = ConnectionPool.create("src\\main\\resources\\db.properties");
        Init init = new Init();
        init.initDb(pool, "src\\main\\resources\\init.sql");
        personDao = new PersonDao(pool);
    }

    @Test
    public void getByIdTest() throws Exception {
        assertTrue(personDao.getEntity(1).isPresent());
    }

    @Test
    public void simpleSelect() throws Exception {
        int size = personDao.getAll().size();
        String email = "Anton@mail.com";
        personDao.create(Person.builder()
                .firstName("Anton")
                .lastName("Anton")
                .email(email)
                .password("1")
                .build());
        assertThat(personDao.getAll().size(), is(size+1));
        assertThat(personDao.getByEmailAndPass(email, "1").orElse(null)
                .getFirstName(), is("Anton"));

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
    public void updateTest() throws Exception {
        personDao.create(Person.builder()
                .firstName("Mike")
                .lastName("Mike")
                .email("Mike@Mike.ru")
                .password("123")
                .build());
        assertThat(personDao.getByEmail("Mike@Mike.ru").get().getFirstName(), is("Mike"));

        Person person = Person.builder()
                .firstName("Mi")
                .lastName("Mi")
                .email("Mike@Mike.ru")
                .dob(LocalDate.parse("2000-05-10"))
                .password("111")
                .build();
        personDao.update(person);

        assertThat(personDao.getByEmail("Mike@Mike.ru").orElse(null)
                .getFirstName(), is("Mi"));
    }
}
