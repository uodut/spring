package com.nubia.spring.service.impl;


import javax.annotation.Resource;

import com.nubia.spring.annotationTest.TudouResource;
import com.nubia.spring.dao.PersonDao;
import com.nubia.spring.service.PersonService;

public class PersonServiceBean implements PersonService {
	
	private PersonDao personDao;
	public void save() {
		personDao.print();
	}
	public PersonDao getPersonDao() {
		return personDao;
	}
	@TudouResource(name="personDao")
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	
	
	
}
