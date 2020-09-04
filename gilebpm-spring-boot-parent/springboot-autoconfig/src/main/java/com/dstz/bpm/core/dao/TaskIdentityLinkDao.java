
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface TaskIdentityLinkDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.TaskIdentityLink> {
    void removeByInstId(java.lang.String s);

    void removeByTaskId(java.lang.String s);

    void bulkCreate(java.util.List<com.dstz.bpm.core.model.TaskIdentityLink> list);

    int checkUserOperatorPermission(@org.apache.ibatis.annotations.Param("rights") java.util.Set<java.lang.String> set, @org.apache.ibatis.annotations.Param("taskId") java.lang.String s, @org.apache.ibatis.annotations.Param("instanceId") java.lang.String s1);

    java.util.List<com.dstz.bpm.core.model.TaskIdentityLink> getByTaskId(java.lang.String s);

    int queryTaskIdentityCount(java.lang.String s);

    int queryInstanceIdentityCount(java.lang.String s);
}