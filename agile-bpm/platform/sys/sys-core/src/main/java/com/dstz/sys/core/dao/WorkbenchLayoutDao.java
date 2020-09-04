package com.dstz.sys.core.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.base.dao.annotation.MapperAnnotation;
import com.dstz.sys.core.model.WorkbenchLayout;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

@MapperAnnotation
public interface WorkbenchLayoutDao extends BaseDao<String, WorkbenchLayout> {

    void removeByUserId(String currentUserId);

    List<WorkbenchLayout> getByUserId(String userId);
}
