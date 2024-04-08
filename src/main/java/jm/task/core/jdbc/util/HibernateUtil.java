package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;


    public static SessionFactory getSession() {
        if (sessionFactory != null) {
            return sessionFactory;
        } else {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            return sessionFactory = configuration.buildSessionFactory();
        }
    }
}
