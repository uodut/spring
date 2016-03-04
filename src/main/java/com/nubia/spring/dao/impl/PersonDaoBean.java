package com.nubia.spring.dao.impl;


import com.nubia.spring.dao.PersonDao;

public class PersonDaoBean implements PersonDao {
	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("PersonDao的print()方法实现");
	}
}