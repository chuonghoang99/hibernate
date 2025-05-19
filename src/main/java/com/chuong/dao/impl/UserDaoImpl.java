package com.chuong.dao.impl;

import com.chuong.dao.UserDao;
import com.chuong.entity.User;
import com.chuong.utils.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Log4j2
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả Users: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            log.error("Lỗi khi lấy User với ID={}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            log.info("User được tạo thành công: {}", user.getName());
        } catch (Exception e) {
            log.error("Lỗi khi tạo User: {}", e.getMessage(), e);
        }
    }

    @Override
    public void updateUser(int id, String newName, String newTech) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                user.setName(newName);
                user.setTech(newTech);
                session.getTransaction().commit();
                log.info("User với ID={} đã được cập nhật.", id);
            } else {
                log.warn("Không tìm thấy User với ID={}", id);
            }
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật User: {}", e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                session.getTransaction().commit();
                log.info("User với ID={} đã bị xóa.", id);
            } else {
                log.warn("Không tìm thấy User với ID={}", id);
            }
        } catch (Exception e) {
            log.error("Lỗi khi xóa User: {}", e.getMessage(), e);
        }
    }
}