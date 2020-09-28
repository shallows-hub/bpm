package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.OrgRelation;
import com.dstz.org.core.model.Role;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/8-16:48
 */
//@Component
public class OrgRelationDaoImpl extends OdooDao implements OrgRelationDao {

    @Override
    public List<OrgRelation> getUserRelation(String userId, String relationType) {
        if (relationType == null){
            return  this.getOdooObjects("/OrgRelationDao/getUserRelation/"+ userId.trim(), OrgRelation.class);
        }
        return  this.getOdooObjects("/OrgRelationDao/getUserRelation/"+ userId.trim() + "/" + relationType.trim(), OrgRelation.class);
    }

    @Override
    public void removeByUserId(String userId) {

    }

    @Override
    public List<OrgRelation> getGroupPost(String groupId) {
        return null;
    }

    @Override
    public void removeGroupPostByGroupId(String id) {

    }

    @Override
    public List<OrgRelation> getRelationsByParam(List<String> relationTypes, String userId, String groupId, String roleId) {
        return null;
    }

    @Override
    public int getCountByRelation(OrgRelation relation) {
        return 0;
    }

    @Override
    public List<OrgRelation> getUserRole(String userId) {
        List<OrgRelation> result=  this.getOdooObjects("/OrgRelationDao/getUserRole/"+ userId.trim(), OrgRelation.class);
        return  result;
    }

    @Override
    public OrgRelation getPost(String id) {
        return null;
    }

    @Override
    public void create(OrgRelation entity) {

    }

    @Override
    public Integer update(OrgRelation entity) {
        return null;
    }

    @Override
    public void remove(String entityId) {

    }

    @Override
    public OrgRelation get(String entityId) {
        return null;
    }

    @Override
    public List<OrgRelation> query(QueryFilter queryFilter) {
        return this.postOdooObjects("/OrgRelationDao/query", queryFilter, OrgRelation.class).toPage(queryFilter);
    }

    @Override
    public List<OrgRelation> query() {
        return null;
    }

    @Override
    public Page<?> queryMap(QueryFilter queryFilter) {
        Page<Map<String,Object>> list = new Page<>();
        List<OrgRelation> results = this.query(queryFilter);
        for (OrgRelation result:results) {
            list.add(result.toSqlMap());
        }
        return list;
    }
}
