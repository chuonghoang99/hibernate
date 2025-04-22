package com.chuong;

import com.chuong.config.HibernateUtils;
import com.chuong.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Lấy SessionFactory
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

        try {
            // Create
            createUser(sessionFactory, new User(1, "Chuong", "JavaScript"));
            createUser(sessionFactory, new User(2, "Thanh", "Python"));

            // Read (lấy một User theo ID)
            User user = readUserById(sessionFactory, 1);
            System.out.println("User đọc được: ID=" + user.getId() + ", Name=" + user.getName() + ", Tech=" + user.getTech());

            // Read (lấy tất cả Users)
            List<User> users = readAllUsers(sessionFactory);
            System.out.println("Danh sách tất cả Users:");
            for (User u : users) {
                System.out.println("ID=" + u.getId() + ", Name=" + u.getName() + ", Tech=" + u.getTech());
            }

            // Update
            updateUser(sessionFactory, 1, "Chuong Updated", "Java");
            System.out.println("User sau khi cập nhật:");
            user = readUserById(sessionFactory, 1);
            System.out.println("ID=" + user.getId() + ", Name=" + user.getName() + ", Tech=" + user.getTech());

            // Delete
            deleteUser(sessionFactory, 2);
            System.out.println("User với ID=2 đã bị xóa.");

            // Kiểm tra lại danh sách Users sau khi xóa
            users = readAllUsers(sessionFactory);
            System.out.println("Danh sách Users sau khi xóa:");
            for (User u : users) {
                System.out.println("ID=" + u.getId() + ", Name=" + u.getName() + ", Tech=" + u.getTech());
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi thực hiện CRUD: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng SessionFactory
            HibernateUtils.close();
        }
    }

    // Create: Thêm một User mới
    private static void createUser(SessionFactory sessionFactory, User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("User được tạo thành công: " + user.getName());
        } catch (Exception e) {
            System.err.println("Lỗi khi tạo User: " + e.getMessage());
        }
    }

    // Read: Lấy một User theo ID
    private static User readUserById(SessionFactory sessionFactory, int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc User với ID=" + id + ": " + e.getMessage());
            return null;
        }
    }

    // Read: Lấy tất cả Users
    private static List<User> readAllUsers(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc tất cả Users: " + e.getMessage());
            return List.of();
        }
    }

    // Update: Cập nhật thông tin User
    private static void updateUser(SessionFactory sessionFactory, int id, String newName, String newTech) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                user.setName(newName);
                user.setTech(newTech);
                session.merge(user);
                session.getTransaction().commit();
                System.out.println("User với ID=" + id + " đã được cập nhật.");
            } else {
                System.out.println("Không tìm thấy User với ID=" + id);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật User: " + e.getMessage());
        }
    }

    // Delete: Xóa một User
    private static void deleteUser(SessionFactory sessionFactory, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                session.getTransaction().commit();
                System.out.println("User với ID=" + id + " đã bị xóa.");
            } else {
                System.out.println("Không tìm thấy User với ID=" + id);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa User: " + e.getMessage());
        }
    }
}