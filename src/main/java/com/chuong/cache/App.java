package com.chuong.cache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chuong.utils.HibernateUtils;
import com.chuong.entity.User;

public class App {
  public static void main(String[] args) {

    SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    initializeSampleData(sessionFactory);

    // Demo First-Level Cache
    testFirstLevelCache(sessionFactory);

    HibernateUtils.close();
  }

  // Phương thức khởi tạo dữ liệu mẫu
  public static void initializeSampleData(SessionFactory sessionFactory) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      // Thêm một User mẫu
      User user = new User("Nguyen Van A", "Java");
      session.save(user);

      session.getTransaction().commit();
      System.out.println("Đã khởi tạo dữ liệu mẫu thành công!");
    } catch (Exception e) {
      System.err.println("Lỗi khi khởi tạo dữ liệu mẫu: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Phương thức demo First-Level Cache
  public static void testFirstLevelCache(SessionFactory sessionFactory) {
    System.out.println("\n=== Demo First-Level Cache ===");
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();

      // Lần 1: Truy xuất User với id=1
      System.out.println("Lần 1: Truy xuất User với id=1");
      User user1 = session.get(User.class, 1);
      if (user1 != null) {
        System.out.println("User: " + user1.getName() + ", Tech: " + user1.getTech());
      } else {
        System.out.println("Không tìm thấy User với id=1");
      }

      // Lần 2: Truy xuất lại User với id=1 trong cùng Session
      System.out.println("\nLần 2: Truy xuất lại User với id=1");
      User user2 = session.get(User.class, 1);
      if (user2 != null) {
        System.out.println("User: " + user2.getName() + ", Tech: " + user2.getTech());
      } else {
        System.out.println("Không tìm thấy User với id=1");
      }

      // Kiểm tra xem user1 và user2 có phải cùng instance không và kiểm tra
      // xem câu select có bị gọi 2 lần không
      System.out.println("\nKiểm tra: user1 == user2: " + (user1 == user2));

      session.getTransaction().commit();
    } catch (Exception e) {
      System.err.println("Lỗi khi demo First-Level Cache: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
