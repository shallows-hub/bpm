package com.dstz.org.core.dao;


import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.User;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Component
public class GroupDaoImpl extends OdooDao implements GroupDao {
    public Group getByCode(String code){
        return this.getOdooObject("/GroupDao/getByCode/"+ code.trim(), Group.class);
    }

    @Override
    public List<Group> getByUserId(String userId) {
        return this.getOdooObjects("/GroupDao/getByUserId/" + userId.trim(), Group.class);
    }

    @Override
    public List<Group> getChildByPath(String path) {
        return null;
    }

    @Override
    public void create(Group entity) {

    }

    @Override
    public Integer update(Group entity) {
        return null;
    }

    @Override
    public void remove(String entityId) {

    }

    @Override
    public Group get(String entityId) {
        return this.getOdooObject("/GroupDao/get/" + entityId.trim(), Group.class);
    }

    @Override
    public List<Group> query(QueryFilter queryFilter) {
        return this.getOdooObjects("/GroupDao/query", Group.class);
    }

    @Override
    public List<Group> query() {
        return this.getOdooObjects("/GroupDao/query", Group.class);
    }

    @Override
    public Page<?> queryMap(QueryFilter queryFilter) {
        Page<Map<String,Object>> list = new Page<>();
        List<Group> results = this.query(queryFilter);
        for (Group result:results) {
            list.add(result.toSqlMap());
        }
        return list;
    }
}
