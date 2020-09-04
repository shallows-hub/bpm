package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.dao.OdooDao;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/4-14:25
 */

@Component
public class UserDaoImpl extends OdooDao implements UserDao {
    @Override
    public User getByAccount(String account) {
        return this.getOdooObject("/UserDao/getByAccount/" + account, User.class);
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
        return this.getOdooObject("/UserDao/get/" + entityId, User.class);
    }

    @Override
    public List<User> query(QueryFilter queryFilter) {
        return this.getOdooObjects("/UserDao/query", User.class);
    }

    @Override
    public List<User> query() {
        return null;
    }
}
