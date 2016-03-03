package com.nubia.spring;

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
/**
 * 2016年3月3日
 * @author <a href = "wang.gaoliang@zte.com.cn">王高亮</a>
 * 自制容器
 */
public class TudouClassPathXmlApplicationContext {
	private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();
	private Map<String, Object> sigletons = new HashMap<String, Object>();
	
	public TudouClassPathXmlApplicationContext(String filename){
		readXMLDocument(filename);
		instanceBeans();
		injectObject();
	}
	/**
	 * 为Bean对象注入属性值
	 * 1、遍历bean,获取bean对应的对象值
	 * 	2、得到bean对象的所有属性，并和name值相比较，如果相等，则找到set方法
	 * 3、把ref对应的对象通过set方法注入
	 */
	private void injectObject() {
		for(BeanDefinition beanDefinition:beanDefines){
			Object bean = sigletons.get(beanDefinition.getId());//bean对应的对象
			if(bean != null){
				//得到所有的属性值
				try {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
					//Field[] fileds = Class.forName(beanDefinition.getClassName()).getDeclaredFields();
					for(PropertyDescriptor propertyDescriptor:ps){
						for(PropertyDefinition propertyDefinition:beanDefinition.getPropertyDefinitions()){
							//如果name的值和属性值相同
							if(propertyDefinition.getName().equals(propertyDescriptor.getName())){
								//找到属性的值对应的set方法
								Method setter = propertyDescriptor.getWriteMethod();//获取属性的setter方法 ,private
								if(setter != null){
									//把ref对应的对象注入进去
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
		for(BeanDefinition beanDefinition : beanDefines){
			try {
				if(beanDefinition.getClassName()!=null && !"".equals(beanDefinition.getClassName().trim()))
					//Class.forName(beanDefinition.getClassName()).newInstance()：获取beanDefintion的newInstance()
					sigletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 读取xml配置文件
	 * @param filename
	 */
	public void readXMLDocument(String path){
	       SAXReader saxReader = new SAXReader();   
	        Document document=null;   
	        try{
	         URL xmlpath = this.getClass().getClassLoader().getResource(path);
	         document = saxReader.read(xmlpath);
	         Map<String,String> nsMap = new HashMap<String,String>();
	         nsMap.put("ns","http://www.springframework.org/schema/beans");//加入命名空间
	         XPath xsub = document.createXPath("//ns:beans/ns:bean");//创建beans/bean查询路径
	         xsub.setNamespaceURIs(nsMap);//设置命名空间
	         List<Element> beans = xsub.selectNodes(document);//获取文档下所有bean节点 
	         for(Element element: beans){
	            String id = element.attributeValue("id");//获取id属性值
	            String clazz = element.attributeValue("class"); //获取class属性值        
	            BeanDefinition beanDefine = new BeanDefinition(id, clazz);
	            //每个bean节点下面会有n个property节点
	            XPath propertysub =  element.createXPath("ns:property");
	            propertysub.setNamespaceURIs(nsMap);//设置命名空间
	            List<Element> propertys = propertysub.selectNodes(element);
	            for(Element property:propertys){
	            	String name = property.attributeValue("name");
	            	String ref = property.attributeValue("ref");
	            	PropertyDefinition propertyDefinition = new PropertyDefinition(name,ref);
	            	beanDefine.getPropertyDefinitions().add(propertyDefinition);
	            }
	            beanDefines.add(beanDefine);
	            
	         }   
	        }catch(Exception e){   
	            e.printStackTrace();
	        }
	}
	/**
	 * 获取bean实例
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName){
		return sigletons.get(beanName);
	}
}
