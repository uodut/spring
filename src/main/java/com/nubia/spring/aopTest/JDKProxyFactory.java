package com.nubia.spring.aopTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.nubia.spring.dao.impl.PersonDaoBean;

/**
 * 2016年3月4日
 * @author <a href = "wang.gaoliang@zte.com.cn">王高亮</a>
 * 拦截:在别的类调用自己的方法的时候通过拦截器判断该类是否有权限执行此操作
 */
public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject;//目标对象
	public Object getInstance(Object obj){
		this.targetObject = obj;
		ClassLoader loader = this.targetObject.getClass().getClassLoader();
		Class<?>[] interfaces = this.targetObject.getClass().getInterfaces();
		return Proxy.newProxyInstance(loader, interfaces, this);
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		PersonDaoBean bean = (PersonDaoBean)this.targetObject;
		Object obj = null;
		if (bean.getUser() != null) {
			System.out.println("代理检测user不为空，可以调用此方法");
			obj = method.invoke(targetObject, args);
		}else{
			System.out.println("代理检测user为空，没有权限调用它所属方法");
		}
		return obj;
	}
}
