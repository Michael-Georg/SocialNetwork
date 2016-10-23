package servlets.listeners;

import Dao.ConnectionPool;
import Dao.PersonDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebListener
public class Init implements ServletContextListener {
    private static final String config = "WEB-INF/classes/db.properties";
    private static final String PERSON_DAO = "personDao";
    private static final String pathToInit = "WEB-INF/classes/init.sql";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        ConnectionPool connectionPool = ConnectionPool.create(realPath + config);
        initDb(connectionPool, realPath + pathToInit);
        context.setAttribute(PERSON_DAO, new PersonDao(connectionPool));
    }

    public void initDb(ConnectionPool connectionPool, String pathToInit) {
        try (Stream<String> fileStream = Files.lines(Paths.get(pathToInit));
             Connection con = connectionPool.get();
             Statement statement = con.createStatement()) {
            Arrays.stream(fileStream.collect(Collectors.joining())
                    .split(";"))
                    .forEachOrdered(sql -> {
                        try {
                            statement.addBatch(String.valueOf(sql));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
            statement.executeBatch();
        } catch (IOException | SQLException e) {
            e.printStackTrace();

        }
    }
}
