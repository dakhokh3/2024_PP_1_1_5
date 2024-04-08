package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = HibernateUtil.getSession();

    private static final Logger log = Logger.getLogger(UserDaoHibernateImpl.class.getName());


    @Override
    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS users (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL,
                  PRIMARY KEY (`id`))
                """;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
            log.info("\u001B[32m Создана таблица users \u001B[0m");
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "Ошибка при создании таблицы Users:", e);
        }

    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
            log.info("\u001B[32m Таблица users удалена \u001B[0m");
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "Ошибка при удалении таблицы Users:", e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
            log.info("\u001B[32m Пользователь успешно добавлен в таблицу Users \u001B[0m");
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "Ошибка при добавлении в таблицу users пользователя " + name, e);
        }


    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            log.info("\u001B[32m Пользователь удален из БД \u001B[0m");
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "Ошибка при удалении пользователя:", e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            log.info("\u001B[32m Получен список пользователей \u001B[0m");
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "Ошибка при получении всех пользователей:", e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
            log.info("\u001B[32m Таблица users очищена \u001B[0m");
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "Ошибка при очистке таблицы users:", e);
        }
    }
}
