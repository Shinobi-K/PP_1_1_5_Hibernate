package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Jimin1995";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private static SessionFactory sessionFactory;

    private static Properties getHibernateProperties() {
        Properties props = new Properties();
        props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.URL, DB_URL);
        props.put(Environment.USER, DB_USERNAME);
        props.put(Environment.PASS, DB_PASSWORD);
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        props.put(Environment.HBM2DDL_AUTO, "");
        props.put(Environment.SHOW_SQL, "true");
        return props;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperties(getHibernateProperties());

                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}