package com.chuong.cache;

import com.chuong.entity.User;
import com.chuong.utils.HibernateUtils;
import jakarta.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TestLifeC {

  public static void main(String[] args) {
    SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    // Tạo và lưu một User ban đầu => transient
    User user = new User("Chuong", "Java");


    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.persist(user); // Lưu user, trở thành Persistent

      user.setName("Chuong update");

      session.getTransaction().commit();
    } // Session đóng, user trở thành Detached


    // xu ly






    // Thử persist() với đối tượng Detached
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      user.setName("Chuong Persist"); // Cập nhật tên
      try {
//        // Test1
//        session.save(user);

        // Test2
        session.persist(user); // Sẽ ném ngoại lệ vì user là Detached
          session.getTransaction().commit();
      } catch (PersistenceException e) {
        System.out.println("PersistenceException:" + e.getMessage());

      } catch (Exception e) {
        System.out.println("persist() thất bại với Detached: " + e.getMessage());
        session.getTransaction().rollback();
      }
    }
  }
}
