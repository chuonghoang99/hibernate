package com.chuong.dao;

import com.chuong.entity.Customer;

import java.util.List;

public interface CustomerDao {

    void saveCustomer(Customer customer);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();

    void updateCustomer(Customer customer);

    void deleteCustomer(Long id);

}
