package com.nubia.spring.aopTest;

import com.nubia.spring.dao.PersonDao;
import com.nubia.spring.dao.impl.PersonDaoBean;

public class TudouAopTest {
	public static void main(String[] args) {
		JDKProxyFactory proxy = new JDKProxyFactory();
		PersonDao personDao = (PersonDao)proxy.getInstance(new PersonDaoBean());
		personDao.print();
	}
}
