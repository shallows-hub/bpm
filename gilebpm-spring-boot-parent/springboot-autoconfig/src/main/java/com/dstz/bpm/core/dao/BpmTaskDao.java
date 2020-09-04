
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface BpmTaskDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.BpmTask> {
    java.util.List<com.dstz.bpm.core.model.BpmTask> getByInstIdNodeId(@org.apache.ibatis.annotations.Param("instId") java.lang.String s, @org.apache.ibatis.annotations.Param("nodeId") java.lang.String s1);

    java.util.List<com.dstz.bpm.core.model.BpmTask> getTodoList(com.dstz.base.api.query.QueryFilter queryFilter);

    void removeByInstId(@org.apache.ibatis.annotations.Param("instId") java.lang.String s);

    java.util.List<com.dstz.bpm.core.model.BpmTask> getByParentId(@org.apache.ibatis.annotations.Param("parentId") java.lang.String s);
}