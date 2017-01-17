package com.runbuddy.dao;

/**
 * @Author Johnny Chou
 * @Create 2017-01-17-22:49
 **/


import com.runbuddy.model.User;

public interface UserDao {
    public boolean isLogin(User user);

    public int updateUserPassWord(User user);

    public boolean isSuperLogin(User user);
}
