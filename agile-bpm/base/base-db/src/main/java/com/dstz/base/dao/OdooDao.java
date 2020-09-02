package com.dstz.base.dao;

import com.dstz.base.core.util.OdooUtily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OdooDao {
    @Autowired
    private OdooUtily odooUtily;

    public OdooUtily getOdooUtily() {
        return odooUtily;
    }

    public void setOdooUtily(OdooUtily odooUtily) {
        this.odooUtily = odooUtily;
    }

    protected  <T> T  getOdooObject(String path, java.lang.Class<T> valueType){
        return this.odooUtily.getOdooObject(path, valueType);
    }
    protected <T> List<T> getOdooObjects(String path, java.lang.Class<T> valueType){
        return this.odooUtily.getOdooObjects(path, valueType);
    }

    protected int postOdooObject(Object o, String path) {
        return this.odooUtily.postOdooObject(o, path);
    }
}