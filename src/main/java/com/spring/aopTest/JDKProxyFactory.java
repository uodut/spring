package com.spring.aopTest;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.spring.dao.impl.PersonDaoBean;
import com.spring.utils.Log;
/**
 * 2016年3月4日
 * 
 * @author <a href = "wang.gaoliang@zte.com.cn">王高亮</a>
 *         拦截:在别的类调用自己的方法的时候通过拦截器判断该类是否有权限执行此操作
 */
public class JDKProxyFactory implements InvocationHandler {
    private Object targetObject;// 目标对象
    private String message = null;
    public Object getInstance(Object obj) {
        this.targetObject = obj;
        ClassLoader loader = this.targetObject.getClass().getClassLoader();
        Class<?>[] interfaces = this.targetObject.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PersonDaoBean bean = (PersonDaoBean) this.targetObject;
        Object obj = null;
        if (bean.getUserInfo() != null) {
            try {
                // 前置通知
                message = "代理检测user不为空，可以调用此方法";
                Log.createInstance(JDKProxyFactory.class).info(message);
                //Log.createInstance(JDKProxyFactory.class).info();
                obj = method.invoke(targetObject, args);
                // 后置通知
            } catch (NullPointerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // 例外通知
            } finally{
                // 最终通知
                Log.createInstance(JDKProxyFactory.class).info("finally");
            }
        } else {
            Log.createInstance(JDKProxyFactory.class).info("代理检测user为空，没有权限调用它所属方法");
        }
        return obj;
    }
}
