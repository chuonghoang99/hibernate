package com.chuong.dao;

import com.chuong.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(int id);

    void createUser(User user);

    void updateUser( int id, String newName, String newTech);

    void deleteUser(int id);
}
