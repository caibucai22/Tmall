package com.caibucai.dao;

import com.caibucai.bean.User;

import java.util.List;

public interface UserDao {

    int getToatal();

    void add(User bean);

    void update(User bean);

    void delete(int id);

    User get(int id);

    List<User> list();

    List<User> list(int start, int count);

    boolean isExist(String name);

    User get(String name);

    User get(String name, String password);
}
