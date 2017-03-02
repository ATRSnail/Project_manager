package com.project.project_manager.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/1
 */

public class LoginBodyBean {

    private LoginBean user;

    public LoginBean getUser() {
        return user;
    }

    public void setUser(LoginBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginBodyBean{" +
                "user=" + user +
                '}';
    }
}
