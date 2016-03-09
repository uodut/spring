package com.spring.aopTest;
//import com.nubia.spring.dao.PersonDao;
import com.spring.dao.impl.PersonDaoBean;
public class TudouAopTest {
    public static void main(String[] args) {
        // JDKProxyFactory proxy = new JDKProxyFactory();
        CglibProxyFactory proxy = new CglibProxyFactory();
        PersonDaoBean personDao = (PersonDaoBean) proxy.getInstance(new PersonDaoBean("not null"));
        personDao.print();
    }
}
