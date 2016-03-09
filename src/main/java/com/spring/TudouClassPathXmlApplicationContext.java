package com.spring;
//import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import com.spring.annotationTest.TudouResource;
/**
 * 2016年3月3日
 * 
 * @author <a href = "wang.gaoliang@zte.com.cn">王高亮</a> 自制容器
 */
public class TudouClassPathXmlApplicationContext {
    // beanDefines存储XML里的bean对象
    private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();
    // String=id,Object=Bean对象，sigletons的object对象存储bean里面的class对象
    private Map<String, Object> sigletons = new HashMap<String, Object>();
    public TudouClassPathXmlApplicationContext(String filename) {
        readXMLDocument(filename);
        instanceBeans();
        // 注入ref
        injectObject();
        // 注入普通对象
        // injectPlainProperty();
        // 注解注入
        annotationInject();
    }
    /**
     * 1、遍历bean,获取bean对象 2、分别检查类的成员变量和setter方法 对于成员变量 1）查看是否有相应注解标注
     * 如果有：查找注解的name值，如果有name值 对于setter()方法
     * 
     */
    private void annotationInject() {
        for (String beanName : sigletons.keySet()) {
            Object bean = sigletons.get(beanName);
            if (bean != null) {
                try {
                    // 对bean的成员变量进行判断
                    Field[] fields = bean.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        // 属性上面是否有注解
                        boolean isAnnotationPresent = field
                                .isAnnotationPresent(TudouResource.class);
                        if (isAnnotationPresent) {
                            // TudouReSource tudouReSource = new
                            // TudouReSource();//注解不是类，不能实例
                            TudouResource tudouReSource = field.getAnnotation(TudouResource.class);
                            // 根据名字获取的bean对象
                            Object value = null;
                            // 注解名字如果不为空
                            if (tudouReSource.name() != null && !"".equals(tudouReSource.name())) {
                                // bean实例
                                value = sigletons.get(tudouReSource.name());
                            } else {
                                // 注解名字为空，则要先判断名字是否相同，相同则赋值，不同判断类型是否相同
                                value = sigletons.get(tudouReSource.name());
                                // value为空，说明名字不存在
                                if (value == null) {
                                    // 判断类型
                                    for (String key : sigletons.keySet()) {
                                        // 如果属性的类型和bean类型相同或者是被它继承（isAssignableFrom）
                                        if (field.getType().isAssignableFrom(
                                                sigletons.get(key).getClass())) {
                                            value = sigletons.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            // 给属性赋值
                            field.setAccessible(true);// 防止属性访问权限是私有的而不能赋值
                            field.set(bean, value);
                        }
                    }
                    // 对setter方法注入值
                    PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(
                            bean.getClass()).getPropertyDescriptors();
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        Method method = propertyDescriptor.getWriteMethod();// 写方法，即setter方法
                        // 如果setter不为空并且上面有注解
                        if (method != null && method.isAnnotationPresent(TudouResource.class)) {
                            // 注解是否存在name值，区别于是否写出来了，没表示出来也有默认的值
                            TudouResource tudouResource = method.getAnnotation(TudouResource.class);
                            Object value = null;
                            if (tudouResource.name() != null && !"".equals(tudouResource.name())) {
                                value = sigletons.get(tudouResource.name());
                            } else {
                                // 比较类型
                                // 通过属性名找对应的bean
                                value = sigletons.get(propertyDescriptor.getName());
                                // 如果找不到，就按类型进行查找
                                if (value == null) {
                                    // 遍历所有的bean对象
                                    for (String key : sigletons.keySet()) {
                                        // 如果属性类型和bean类型一致
                                        if (propertyDescriptor.getPropertyType().isAssignableFrom(
                                                sigletons.get(key).getClass())) {
                                            value = sigletons.get(key);
                                            break;
                                        }
                                    }
                                }
                            }
                            method.setAccessible(true);
                            method.invoke(bean, value);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    private void injectPlainProperty() {
    }
    /**
     * 为Bean对象注入属性值 1、遍历bean,获取bean对应的对象值
     * 2、得到bean对象的所有属性，并和name值相比较，如果相等，则找到set方法 3、把ref对应的对象通过set方法注入
     */
    private void injectObject() {
        for (BeanDefinition beanDefinition : beanDefines) {
            Object bean = sigletons.get(beanDefinition.getId());// bean对应的对象
            if (bean != null) {
                // 得到所有的属性值
                try {
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass())
                            .getPropertyDescriptors();
                    // Field[] fileds =
                    // Class.forName(beanDefinition.getClassName()).getDeclaredFields();
                    for (PropertyDescriptor propertyDescriptor : ps) {
                        for (PropertyDefinition propertyDefinition : beanDefinition
                                .getPropertyDefinitions()) {
                            // 如果name的值和属性值相同
                            if (propertyDefinition.getName().equals(propertyDescriptor.getName())) {
                                // 找到属性的值对应的set方法
                                Method setter = propertyDescriptor.getWriteMethod();// 获取属性的setter方法
                                                                                    // ,private
                                if (setter != null) {
                                    // 把ref对应的对象注入进去
                                    Object value = sigletons.get(propertyDefinition.getRef());
                                    setter.invoke(bean, value);
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 完成bean的实例化
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefines) {
            try {
                if (beanDefinition.getClassName() != null
                        && !"".equals(beanDefinition.getClassName().trim()))
                    // Class.forName(beanDefinition.getClassName()).newInstance()：获取beanDefintion的newInstance()
                    sigletons.put(beanDefinition.getId(),
                            Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 读取xml配置文件
     * 
     * @param filename
     */
    public void readXMLDocument(String path) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            URL xmlpath = this.getClass().getClassLoader().getResource(path);
            document = saxReader.read(xmlpath);
            Map<String, String> nsMap = new HashMap<String, String>();
            nsMap.put("ns", "http://www.springframework.org/schema/beans");// 加入命名空间
            XPath xsub = document.createXPath("//ns:beans/ns:bean");// 创建beans/bean查询路径
            xsub.setNamespaceURIs(nsMap);// 设置命名空间
            List<Element> beans = xsub.selectNodes(document);// 获取文档下所有bean节点
            for (Element element : beans) {
                String id = element.attributeValue("id");// 获取id属性值
                String clazz = element.attributeValue("class"); // 获取class属性值
                BeanDefinition beanDefine = new BeanDefinition(id, clazz);
                // 每个bean节点下面会有n个property节点
                XPath propertysub = element.createXPath("ns:property");
                propertysub.setNamespaceURIs(nsMap);// 设置命名空间
                List<Element> propertys = propertysub.selectNodes(element);
                for (Element property : propertys) {
                    String name = property.attributeValue("name");
                    String ref = property.attributeValue("ref");
                    PropertyDefinition propertyDefinition = new PropertyDefinition(name, ref);
                    beanDefine.getPropertyDefinitions().add(propertyDefinition);
                }
                beanDefines.add(beanDefine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取bean实例
     * 
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return sigletons.get(beanName);
    }
}
