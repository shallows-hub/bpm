package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.org.core.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

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
//        return this.getOdooObjects("/GroupDao/getByUserId/" + userId, Role.class);
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
        return this.getOdooObject("/RoleDao/get/" + entityId, Role.class);
    }

    @Override
    public List<Role> query(QueryFilter queryFilter) {

        return this.postOdooObjects("/RoleDao/query", queryFilter, Role.class);
    }

    @Override
    public List<Role> query() {
        return this.getOdooObjects("/RoleDao/query", Role.class);
    }
}
