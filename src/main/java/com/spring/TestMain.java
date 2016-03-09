package com.spring;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import com.nubia.spring.dao.impl.PersonDaoBean;
import com.spring.service.PersonService;
public class TestMain {
    public TestMain() {
        // TODO Auto-generated constructor stub
    }
    public static void main(String[] args) {
        // ApplicationContext act = new
        // ClassPathXmlApplicationContext("beanTest.xml");
        TudouClassPathXmlApplicationContext act = new TudouClassPathXmlApplicationContext(
                "beanTest.xml");
        PersonService pdl = (PersonService) act.getBean("personService");
        pdl.save();
    }
}
