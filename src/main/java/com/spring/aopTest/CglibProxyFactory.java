package com.spring.aopTest;
import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import com.spring.dao.impl.PersonDaoBean;
/**
 * 
 * cglib方式实现代理： 原理是继承要代理的类，实现除了final以外的方法。
 *
 */
public class CglibProxyFactory implements MethodInterceptor {
    private Object targetObject;
    public Object getInstance(Object targetObject) {
        this.targetObject = targetObject;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.targetObject.getClass());// 非final
        enhancer.setCallback(this);
        return enhancer.create();
    }
    /**
     * proxy:代理本身 method:要代理的方法
     * 
     */
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy)
            throws Throwable {
        PersonDaoBean bean = (PersonDaoBean) this.targetObject;
        Object result = null;
        if (bean.getUserInfo() != null) {
            result = methodProxy.invoke(targetObject, args);
        }
        return result;
    }
}
