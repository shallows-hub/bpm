package com.dstz.form.util;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.rest.util.OdooDao;
import com.dstz.form.model.FormCustDialog;
import com.dstz.org.core.dao.GroupDaoImpl;
import com.dstz.org.core.dao.RoleDaoImpl;
import com.dstz.org.core.dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * author hj
 * Description Form Dialog 里，部分数据直接通过baseDao用sql查询数据库，现新增此class来修改原查询方式为通过对应Dao查询
 * date 2020/9/10-9:50
 */
@Component
public class FormDialogToDao {
    @Autowired
    UserDaoImpl userDaoImpl;
    @Autowired
    GroupDaoImpl groupDaoImpl;
    @Autowired
    RoleDaoImpl roleDaoImpl;

    private Map<String, OdooDao> daoMap;

    private Map<String, OdooDao> getDaoMap(){
        if (daoMap == null){
            daoMap = new HashMap<>();
            daoMap.put("org_user", userDaoImpl);
            daoMap.put("org_group", groupDaoImpl);
            daoMap.put("org_role", roleDaoImpl);
        }
        return daoMap;
    }
    public  List<?> data(FormCustDialog formCustDialog, QueryFilter queryFilter){
        OdooDao odooDao = getDaoMap().get(formCustDialog.getObjName());
        return odooDao.queryMap(queryFilter);
    }
    public Boolean whetherToData(FormCustDialog formCustDialog){
        return  getDaoMap().containsKey(formCustDialog.getObjName());
    }

}
