package org.example;

import lombok.Getter;
import org.example.entities.User;
import org.example.entities.Account;
import org.example.entities.TransactionHistory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for Hibernate configuration and session factory management.
 * Provides methods to build and access the session factory.
 */
public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    /**
     * -- GETTER --
     * Provides access to the SessionFactory.
     *
     */
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Builds the SessionFactory using the configuration from hibernate.cfg.xml.
     * This method adds annotated entity classes to the configuration and initializes the session factory.
     *
     * @return the initialized SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(TransactionHistory.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Error initializing SessionFactory!", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
