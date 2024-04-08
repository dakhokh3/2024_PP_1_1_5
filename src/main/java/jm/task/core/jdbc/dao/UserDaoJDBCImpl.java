package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS Users (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL,
                  PRIMARY KEY (`id`))
                """;
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            log.info("\u001B[32m Создана таблица Users \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при создании таблицы Users:", e);
        }

    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            log.info("\u001B[32m Таблица Users удалена \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при удалении таблицы Users:", e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users (name,lastname,age) values (?,?,?)";
        try (Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, String.valueOf(age));
            statement.execute();
            log.info("\u001B[32m Пользователь " + name + " успешно добавлен в таблицу Users \u001B[0m");
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при добавлении пользователя " + name, e);
        }

    }

    public void removeUserById(long id) {
        String query = "DELETE FROM Users WHERE ID = " + id;
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            log.info("\u001B[32m Пользователь с id = " + id + " успешно удалён \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при удалении пользователя:", e);
        }

    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM Users";
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                userList.add(new User(name, lastName, age));
            }
            log.info("\u001B[32m Получены все пользователи из таблицы Users \u001B[0m");


        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при получении всех пользователей: ", e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM Users";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            log.info("\u001B[32m Таблица Users успешно очищена \u001B[0m");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при попытке удалить всех пользователей:", e);
        }

    }
}
