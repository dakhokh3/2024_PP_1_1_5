package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Util {
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/my_schema";

    private static final Logger log = Logger.getLogger(Util.class.getName());


    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            log.info("\u001B[32m" + "Создано новое бодключение к БД" + "\u001B[0m");
            return connection;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при создании подключения к БД:", e);
            throw new RuntimeException(e);
        }

    }
}
