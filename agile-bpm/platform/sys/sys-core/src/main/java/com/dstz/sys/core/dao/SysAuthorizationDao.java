package com.dstz.sys.core.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.base.dao.annotation.MapperAnnotation;
import com.dstz.sys.core.model.SysAuthorization;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperAnnotation
public interface SysAuthorizationDao extends BaseDao<String, SysAuthorization> {
	
    public List<SysAuthorization> getByTarget(@Param("rightsObject")String rightsObject, @Param("rightsTarget")String rightsTarget);

    public void deleteByTarget(@Param("rightsObject")String rightsObject, @Param("rightsTarget")String rightsTarget);

}
