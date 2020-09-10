package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.Role;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/7-16:34
 */

@Component
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

        return this.postOdooObjects("/RoleDao/query", queryFilter, Role.class);
    }

    @Override
    public List<Role> query() {
        return this.getOdooObjects("/RoleDao/query", Role.class);
    }

    @Override
    public List<?> queryMap(QueryFilter queryFilter) {
        List<Map<String,Object>> list = new Page<>();
        List<Role> results = this.query(queryFilter);
        for (Role result:results) {
            list.add(result.toSqlMap());
        }
        return list;
    }
}
