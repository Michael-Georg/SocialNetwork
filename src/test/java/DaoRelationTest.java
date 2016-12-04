import Dao.RelationDao;
import Dao.common.ConnectionPool;
import models.Relation;
import org.junit.BeforeClass;
import org.junit.Test;
import servlets.listeners.Init;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DaoRelationTest {
    private static RelationDao relationDao;

    @BeforeClass
    public static void init() throws Exception {
        ConnectionPool pool = ConnectionPool.create("src\\main\\resources\\db.properties");
        Init init = new Init();
        init.initDb(pool, "src\\main\\resources\\init.sql");
        relationDao = new RelationDao(pool);
    }

    @Test
    public void followListTest() throws Exception {
        int size = relationDao.followersList(1).size();
        Relation relation = new Relation(5, 1, 2);
        relationDao.add(relation);
        assertThat(relationDao.followingList(5).size(), is(1));
        assertThat(relationDao.followersList(1).size(), is(size+1));
    }

    @Test
    public void ignoreListTest() throws Exception {
        Relation relation = new Relation(1, 5, 1);
        relationDao.add(relation);
        assertThat(relationDao.ignoreList(5).size(), is(1));
    }
}
