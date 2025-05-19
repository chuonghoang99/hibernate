package com.chuong.getloadfind;

import jakarta.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chuong.utils.HibernateUtils;
import com.chuong.entity.User;

public class App {

  public static void main(String[] args) {
    SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    // Gọi các phương thức kiểm thử
    testGetMethod(sessionFactory);
    testLoadMethod(sessionFactory);
    testFindMethod(sessionFactory);
  }

  // Phương thức kiểm thử get()
  public static void testGetMethod(SessionFactory sessionFactory) {
    System.out.println("=== Sử dụng get() ===");
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      // Truy xuất User với ID = 1
      User userGet = session.get(User.class, 1);
      if (userGet != null) {
        System.out.println("User (get): " + userGet.getName() + ", Tech: " + userGet.getTech());
      } else {
        System.out.println("Không tìm thấy User với ID = 1 (get)");
      }

      session.getTransaction().commit();
    } catch (Exception e) {
      System.err.println("Lỗi khi sử dụng get(): " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Phương thức kiểm thử load()
  public static void testLoadMethod(SessionFactory sessionFactory) {
    System.out.println("\n=== Sử dụng load() ===");
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      // Truy xuất User với ID = 1
      User userLoad = session.load(User.class, 1);
      // Lúc này chưa truy vấn, chỉ tạo proxy
      System.out.println("Proxy được tạo cho User ID = 1");
      // Truy vấn xảy ra khi truy cập thuộc tính
      System.out.println("User (load): " + userLoad.getName() + ", Tech: " + userLoad.getTech());

      session.getTransaction().commit();
    } catch (Exception e) {
      System.err.println("Lỗi khi sử dụng load(): " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Phương thức kiểm thử find()
  public static void testFindMethod(SessionFactory sessionFactory) {
    System.out.println("\n=== Sử dụng find() ===");
    try (Session session = sessionFactory.openSession()) {
      // Chuyển Session thành EntityManager để dùng JPA
      EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
      entityManager.getTransaction().begin();

      // Truy xuất User với ID = 1
      User userFind = entityManager.find(User.class, 1);
      if (userFind != null) {
        System.out.println("User (find): " + userFind.getName() + ", Tech: " + userFind.getTech());
      } else {
        System.out.println("Không tìm thấy User với ID = 1 (find)");
      }

      entityManager.getTransaction().commit();
      entityManager.close();
    } catch (Exception e) {
      System.err.println("Lỗi khi sử dụng find(): " + e.getMessage());
      e.printStackTrace();
    }
  }
}
