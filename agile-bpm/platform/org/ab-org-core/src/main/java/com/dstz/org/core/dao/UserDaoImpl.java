package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.org.core.model.User;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/4-14:25
 */

@Component
public class UserDaoImpl extends OdooDao implements UserDao {
    @Override
    public User getByAccount(String account) {
        return this.getOdooObject("/UserDao/getByAccount/" + account.trim(), User.class);
    }

    @Override
    public Integer isUserExist(User user) {
        return null;
    }

    @Override
    public List<User> getUserListByRelation(String relId, String type) {
        return null;
    }

    @Override
    public List<User> getUserListByPost(String roleId, String groupId) {
        return null;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public Integer update(User entity) {
        return null;
    }

    @Override
    public void remove(String entityId) {

    }

    @Override
    public User get(String entityId) {
        return this.getOdooObject("/UserDao/get/" + entityId.trim(), User.class);
    }

    @Override
    public List<User> query(QueryFilter queryFilter) {
        return this.getOdooObjects("/UserDao/query", User.class);
    }

    @Override
    public List<User> query() {
        return null;
    }
    @Override
    public Page<?> queryMap(QueryFilter queryFilter) {
        Page<Map<String,Object>> list = new Page<>();
        List<User> results = this.query(queryFilter);
        for (User result:results) {
            list.add(result.toSqlMap());
        }
        return list;
    }
}
