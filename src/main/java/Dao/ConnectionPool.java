package Dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface ConnectionPool extends AutoCloseable {
    BlockingQueue<PooledConnection> getConnectionPool();

    static ConnectionPool create(String pathToConfig) {
        Properties properties = new Properties();
        try (InputStream config = Files.newInputStream(Paths.get(pathToConfig))) {
            properties.load(config);
            Class.forName(properties.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Integer size = Integer.parseInt(properties.getProperty("poolsize"));
        BlockingQueue<PooledConnection> connectionPool =
                new LinkedBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            try {
                connectionPool.put(
                        PooledConnection.create(
                                DriverManager.getConnection(
                                        properties.getProperty("url"),
                                        properties.getProperty("user"),
                                        properties.getProperty("password")),
                                connectionPool));
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        return () -> connectionPool;
    }

    default PooledConnection get() {
        return getConnectionPool().poll();
    }

    default void close() {
        for (PooledConnection con : getConnectionPool()) {
            try {
                con.get().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    default int getActiveConnections(){
        return getConnectionPool().size();
    }
}
