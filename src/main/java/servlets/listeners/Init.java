package servlets.listeners;

import Dao.MessageDao;
import Dao.PersonDao;
import Dao.RelationDao;
import Dao.common.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import org.h2.Driver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static servlets.ServletConst.*;
@Slf4j
@WebListener
public class Init implements ServletContextListener {
    private static ConnectionPool connectionPool;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        connectionPool = ConnectionPool.create(realPath + config);
        initDb(connectionPool, realPath + pathToInit);
        log.info("Connection pool initialize");
        context.setAttribute(FRIENDS_DAO, new RelationDao(connectionPool));
        context.setAttribute(PERSON_DAO, new PersonDao(connectionPool));
        context.setAttribute(MESSAGE_DAO, new MessageDao(connectionPool));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            connectionPool.close();
            Driver driver = Driver.load();
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            log.error("Failed database destroying", e);
        }
        log.info("Database driver destroyed");
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
            log.error("Start database initialization failed", e);
        }
        log.info("Database initialize correct");
    }
}
