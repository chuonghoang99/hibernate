package com.chuong;

import com.chuong.dao.CustomerDao;
import com.chuong.dao.UserDao;
import com.chuong.dao.impl.CustomerDaoImpl;
import com.chuong.dao.impl.UserDaoImpl;
import com.chuong.entity.Customer;
import com.chuong.entity.Item;
import com.chuong.entity.Order;
import com.chuong.entity.User;
import com.chuong.utils.HibernateUtils;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class Main {

    public static void testCRUDUser() {
        // Khởi tạo UserDao
        UserDao userDao = new UserDaoImpl();

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

    public static void testCreateCustomer() {
        CustomerDao customerDao = new CustomerDaoImpl();

        Customer customer = new Customer();
        customer.setName("Chuong Hoang");
        customer.setEmail("chuonng.doe@example.com");

        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setPrice(999.99);

        Item item2 = new Item();
        item2.setName("Mouse");
        item2.setPrice(29.99);

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
        order.getItems().add(item1);
        order.getItems().add(item2);

        customer.getOrders().add(order);
        item1.getOrders().add(order);
        item2.getOrders().add(order);

        customerDao.saveCustomer(customer);


    }

    public static void testGetAllCustomer() {
        CustomerDao customerDao = new CustomerDaoImpl();

        List<Customer> list = customerDao.getAllCustomers();
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {

        //testCRUDUser();

       // testCreateCustomer();


        testGetAllCustomer();

    }

}