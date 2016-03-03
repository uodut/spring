package com.nubia.spring.annotationTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {
	public static void main(String[] args) {
		//String[] locations = { "BeanAnnotation.xml" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext("BeanAnnotation.xml");
		Boss boss = (Boss) ctx.getBean("boss");
		System.out.println(boss.toString());
	}
}
