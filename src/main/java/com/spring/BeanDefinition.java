package com.spring;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
    private String id;
    private String className;
    private List<PropertyDefinition> propertyDefinitions = new ArrayList<PropertyDefinition>();

    public BeanDefinition(String id, String clazz) {
        this.id = id;
        this.className = clazz;
    }

    public List<PropertyDefinition> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public void setPropertyDefinitions(List<PropertyDefinition> propertyDefinitions) {
        this.propertyDefinitions = propertyDefinitions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String clazz) {
        this.className = clazz;
    }

}
