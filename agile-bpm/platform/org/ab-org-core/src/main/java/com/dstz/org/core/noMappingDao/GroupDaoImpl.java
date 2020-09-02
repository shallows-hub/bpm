package com.dstz.org.core.noMappingDao;


import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.dao.OdooDao;
import com.dstz.org.core.noMappingDao.GroupDao;
import com.dstz.org.core.model.Group;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupDaoImpl extends OdooDao implements GroupDao {
    public Group getByCode(String code){
        return this.getOdooObject("/GroupDao/getByCode/"+ code, Group.class);
    }

    @Override
    public List<Group> getByUserId(String userId) {
        return this.getOdooObjects("/GroupDao/getByUserId/" + userId, Group.class);
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
        return this.getOdooObject("/GroupDao/get/" + entityId, Group.class);
    }

    @Override
    public List<Group> query(QueryFilter queryFilter) {
        return null;
    }

    @Override
    public List<Group> query() {
        return this.getOdooObjects("/GroupDao/query", Group.class);
    }
}
