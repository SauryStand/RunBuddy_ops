package com.runbuddy.dao.Impl;

import com.runbuddy.dao.UserDao;
import com.runbuddy.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @Author Johnny Chou
 * @Create 2017-01-17-22:58
 **/
public class UserDaoImpl implements UserDao {
    private static Connection conn = DbConn.getConn();

    public boolean isLogin(User user) {
        boolean flag = false;
        String sql = "select * from BP_TESTING_ACCOUNT where accountname=? and accountpasswd=?";
        try {
            PreparedStatement pmst = conn.prepareStatement(sql);
            pmst.setString(1, user.getUsername());
            pmst.setString(2, user.getPassword());
            ResultSet rs = pmst.executeQuery();
            // 插个超级用户的判断
            if (rs.next()) {
                flag = true;
                System.out.println("flag is " + flag);
            } else {
                System.out.println("flag is " + flag);
            }
        } catch (SQLException e) {
            System.out.println("链接数据库失败");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isSuperLogin(User user){
        boolean flag = false;
        String sql = "select * from BP_TESTING_SUPER_ACCOUNT where SUPER_USER=? and SUPER_USER_PW=?";
        try {
            PreparedStatement pmst = conn.prepareStatement(sql);
            pmst.setString(1, user.getUsername());
            pmst.setString(2, user.getPassword());
            ResultSet rs = pmst.executeQuery();
            // 插个超级用户的判断
            if (rs.next()) {
                flag = true;
                System.out.println("-->>SuperUser login flag is " + flag);
            } else {
                System.out.println("-->>SuperUser login flag is " + flag);
            }
        } catch (SQLException e) {
            System.out.println("超级用户登录失败");
            e.printStackTrace();
        }


        return flag;
    }



    public int updateUserPassWord(User user) {
        int a = 0;
        String sql = "update BP_TESTING_ACCOUNT set accountpasswd=? where accountname=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getUsername());
            a = pstmt.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;
    }
}
