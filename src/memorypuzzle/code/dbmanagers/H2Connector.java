package memorypuzzle.code.dbmanagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс для подключения к базе данных Н2. (реализуется паттерном "Одиночка")
 */
public class H2Connector {
    private Connection conn = null;
    private static H2Connector connector;

    private H2Connector() {
        try {
            Properties connProp = new Properties();
            connProp.put("user", "sa");
            connProp.put("password", "");
            connProp.put("characterEncoding", "UTF-8");

            String URL = "jdbc:h2:" + System.getProperty("user.dir") + "/src/memorypuzzle/database/memoryPuzzle";

            conn = DriverManager.getConnection(URL, connProp);

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public Connection getConnection() {
       return conn;
    }

    public static H2Connector getInstance() {
        if(connector == null)
            connector = new H2Connector();
        return connector;
    }

}

