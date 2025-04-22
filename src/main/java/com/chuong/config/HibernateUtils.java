package com.chuong.config;

import com.chuong.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    private HibernateUtils() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            metadataSources.addAnnotatedClass(User.class);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.err.println("Không thể tạo SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
