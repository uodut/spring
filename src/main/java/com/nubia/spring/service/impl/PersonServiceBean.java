package com.nubia.spring.service.impl;

import com.nubia.spring.dao.PersonDao;
import com.nubia.spring.service.PersonService;

public class PersonServiceBean implements PersonService {
	private PersonDao personDao;
	@Override
	public void save() {
		// TODO Auto-generated method stub
		personDao.print();
	}
	public PersonDao getPersonDao() {
		return personDao;
	}
	//set方法注入
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	
	
	
}
