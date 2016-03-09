package com.spring.service.impl;
//import javax.annotation.Resource;
import com.spring.annotationTest.TudouResource;
import com.spring.dao.PersonDao;
import com.spring.service.PersonService;
public class PersonServiceBean implements PersonService {
    private PersonDao personDao;
    public void save() {
        personDao.print();
    }
    public PersonDao getPersonDao() {
        return personDao;
    }
    @TudouResource(name = "personDao")
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }
}
