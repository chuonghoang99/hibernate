package com.chuong.dao.impl;

import com.chuong.dao.CustomerDao;
import com.chuong.entity.Customer;
import com.chuong.utils.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Log4j2
public class CustomerDaoImpl implements CustomerDao {

    private final SessionFactory sessionFactory;

    public CustomerDaoImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    // Create a new Customer
    public void saveCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error create customer: {}", e.getMessage(), e);
        }
    }

    // Read a Customer by ID
    public Customer getCustomerById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Customer.class, id);
        } catch (Exception e) {

            log.error("Error get customer: {}", e.getMessage(), e);
            return null;
        }
    }

    // Read all Customers
    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Customer> query = session.createQuery("FROM Customer", Customer.class);

            List<Customer> customers = query.list();
            for (Customer customer : customers) {
                Hibernate.initialize(customer.getOrders()); //
            }
            return customers;
        } catch (Exception e) {
            log.error("Error get all customer: {}", e.getMessage(), e);
            return null;
        }
    }

    // Update a Customer
    public void updateCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error update customer: {}", e.getMessage(), e);
        }
    }

    // Delete a Customer by ID
    public void deleteCustomer(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.remove(customer);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error delete customer: {}", e.getMessage(), e);
        }
    }
}