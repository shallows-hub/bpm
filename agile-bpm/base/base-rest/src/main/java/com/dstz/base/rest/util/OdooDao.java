package com.dstz.base.rest.util;

import com.dstz.base.api.query.QueryFilter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public abstract class OdooDao {
    @Autowired
    private OdooUtily odooUtily;

    public OdooUtily getOdooUtily() {
        return odooUtily;
    }

    public void setOdooUtily(OdooUtily odooUtily) {
        this.odooUtily = odooUtily;
    }

    protected  <T> T  getOdooObject(String path, java.lang.Class<T> valueType){
        Object object = this.odooUtily.getObject(path);
        return mapperToObject(object, valueType);

    }
    protected <T> List<T> getOdooObjects(String path, java.lang.Class<T> valueType){
        Object object = this.odooUtily.getObject(path);
        return mapperToObjects(object, valueType);
    }

    protected int postOdooObject(String path,Object o) {
//        return this.odooUtily.postOdooObject(path, o);
        return 0;
    }
    protected <T> List<T> postOdooObjects(String path, Object requestObject, java.lang.Class<T> valueType){
        Object resultObject = this.odooUtily.postObjects(path, requestObject);
//        postObjects
        return mapperToObjects(resultObject, valueType);
    }
    private static  <T> List<T>  mapperToObjects(Object object, java.lang.Class<T> valueType){
        if (null == object){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Page.class, valueType);
        try {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            return (Page<T>) objectMapper.convertValue(object, javaType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private static <T> T  mapperToObject(Object object, java.lang.Class<T> valueType){
        if (null == object){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper.convertValue(object, valueType);
    }
    public abstract List<?> queryMap(QueryFilter queryFilter);
}