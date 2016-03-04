package com.nubia.spring.annotationTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nubia.spring.TudouClassPathXmlApplicationContext;
import com.nubia.spring.service.PersonService;

/**
 * 2016年3月4日
 * @author <a href = "wang.gaoliang@zte.com.cn">王高亮</a>
 * 注解功能测试
 * 属性注解和setter方法注解两种方式
 */
public class AnnoationTest {
	public static void main(String[] args) {
		ApplicationContext act = new ClassPathXmlApplicationContext("BeanAnnotation.xml");
		//TudouClassPathXmlApplicationContext act = new TudouClassPathXmlApplicationContext("BeanAnnotation.xml");
		PersonService personService = (PersonService)act.getBean("personService");
		personService.save();
	}
}
