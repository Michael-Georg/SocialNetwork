import Dao.MessageDao;
import Dao.common.ConnectionPool;
import models.WSMessage;
import org.junit.BeforeClass;
import org.junit.Test;
import servlets.listeners.Init;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class DaoMessageTest {
    private static MessageDao messageDao;

    @BeforeClass
    public static void init() throws Exception {
        ConnectionPool pool = ConnectionPool.create("src\\main\\resources\\db.properties");
        Init init = new Init();
        init.initDb(pool, "src\\main\\resources\\init.sql");
        messageDao = new MessageDao(pool);
    }

    @Test
    public void getAllPostTest(){
        assertThat(messageDao.getAllPosts(2).size(), is(2));
    }


    @Test
    public void getAllCommentTest(){
        assertThat(messageDao.getAllComments(3).size(), is(1));
    }


    @Test
    public void addNewPostTest(){
        WSMessage msg = WSMessage.builder()
                .text("new msg")
                .post_id(-1)
                .user_id(1)
                .time("2017-01-03 20:17:00")
                .build();
        messageDao.add(msg);
        assertThat(messageDao.getAllPosts(1).size(), is(3));
    }

    @Test
    public void addNewCommentTest(){
        WSMessage msg = WSMessage.builder()
                .text("new msg")
                .post_id(1)
                .user_id(1)
                .time("2017-01-03 20:17:00")
                .build();
        messageDao.add(msg);
        assertThat(messageDao.getAllComments(1).size(), is(2));
    }
}