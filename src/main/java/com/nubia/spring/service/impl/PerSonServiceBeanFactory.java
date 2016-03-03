package com.nubia.spring.service.impl;

public class PerSonServiceBeanFactory {
	public static PersonServiceBean createPersonServiceBean(){
		return new PersonServiceBean();
	}
	public PersonServiceBean createPersonServiceBean2(){
		return new PersonServiceBean();
	}
}
