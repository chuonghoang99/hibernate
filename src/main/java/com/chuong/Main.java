package com.chuong;

import com.chuong.dao.UserDao;
import com.chuong.dao.impl.UserDaoImpl;
import com.chuong.entity.User;
import com.chuong.utils.HibernateUtils;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Main {

  public static void main(String[] args) {
    // Khởi tạo UserDao
    UserDao userDao =  new UserDaoImpl();

    try {
      // Create
      userDao.createUser(new User("Chuong", "JavaScript"));
      userDao.createUser(new User("Thanh", "Python"));

      // Read (lấy một User theo ID)
      User user = userDao.getUserById(1);
      if (user != null) {
        log.info("User đọc được: ID={}, Name={}, Tech={}", user.getId(), user.getName(), user.getTech());
      }

      // Read (lấy tất cả Users)
      List<User> users = userDao.getAllUsers();
      log.info("Danh sách tất cả Users:");
      for (User u : users) {
        log.info("ID={}, Name={}, Tech={}", u.getId(), u.getName(), u.getTech());
      }

      // Update
      userDao.updateUser(1, "Chuong Updated", "Go lang");
      log.info("User sau khi cập nhật:");
      user = userDao.getUserById(1);
      if (user != null) {
        log.info("ID={}, Name={}, Tech={}", user.getId(), user.getName(), user.getTech());
      }

      // Delete
      userDao.deleteUser(2);
      log.info("User với ID=2 đã bị xóa.");

      // Kiểm tra lại danh sách Users sau khi xóa
      users = userDao.getAllUsers();
      log.info("Danh sách Users sau khi xóa:");
      for (User u : users) {
        log.info("ID={}, Name={}, Tech={}", u.getId(), u.getName(), u.getTech());
      }

    } catch (Exception e) {
      log.error("Lỗi khi thực hiện CRUD: {}", e.getMessage(), e);
    } finally {
      // Đóng SessionFactory
      HibernateUtils.close();
    }
  }
}