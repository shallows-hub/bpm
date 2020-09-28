package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.base.rest.util.OdooDataWithPage;
import com.dstz.org.core.model.Role;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/7-16:34
 */

//@Component
public class RoleDaoImpl extends OdooDao implements RoleDao {
    @Override
    public Role getByAlias(String alias) {
        return null;
    }

    @Override
    public Integer isRoleExist(Role role) {
        return null;
    }

    @Override
    public List<Role> getByUserId(String userId) {
        return null;
    }

    @Override
    public void create(Role entity) {

    }

    @Override
    public Integer update(Role entity) {
        return null;
    }

    @Override
    public void remove(String entityId) {
    }

    @Override
    public Role get(String entityId) {
        return this.getOdooObject("/RoleDao/get/" + entityId.trim(), Role.class);
    }

    @Override
    public List<Role> query(QueryFilter queryFilter) {
        return this.postOdooObjects("/RoleDao/query", queryFilter, Role.class).toPage(queryFilter);
    }

//    private Page<Role> queryWithFitter(QueryFilter queryFilter){
//        Page<Role> result =  this.postOdooObjects("/RoleDao/query", queryFilter, Role.class);
//        result.setPageSize(queryFilter.getPage().getPageSize());
//        result.setPageNum(queryFilter.getPage().getPageNo());
//        result.setStartRow(1);
//        result.setEndRow(10);
//        result.setTotal(1000);
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            String body = objectMapper.writeValueAsString(result);
//            System.out.println(body);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return  result;
//    }
//    private Page<Role> queryWithFitter(QueryFilter queryFilter){
//        Object object =  this.odooUtily.postObjects("/RoleDao/query", queryFilter);
//        if (null == object){
//            return null;
//        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(OdooDataWithPage.class, Role.class);
//        try {
//            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//            OdooDataWithPage<Role> result =  (OdooDataWithPage<Role>) objectMapper.convertValue(object, javaType);
//
//            Page<Role> pages = result.toPage(queryFilter);
//            int row = pages.getEndRow();
//            return pages;
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public List<Role> query() {
        return this.getOdooObjects("/RoleDao/query", Role.class);
    }

    @Override
    public Page<?> queryMap(QueryFilter queryFilter) {
        Page<Map<String,Object>> list = new Page<>();
        List<Role> results = this.query(queryFilter);
        for (Role result:results) {
            list.add(result.toSqlMap());
        }
        return list;
    }
}
