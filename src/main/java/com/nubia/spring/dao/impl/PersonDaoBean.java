package com.nubia.spring.dao.impl;
import com.nubia.spring.dao.PersonDao;
public class PersonDaoBean implements PersonDao {
    private String userInfo;
    public void setUser(String userInfo) {
        this.userInfo = userInfo;
    }
    public String getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
    public PersonDaoBean(String userInfo) {
        this.userInfo = userInfo;
    }
    public PersonDaoBean() {
    }
    public void print() {
        // TODO Auto-generated method stub
        System.out.println("PersonDao的print()方法实现");
    }
}