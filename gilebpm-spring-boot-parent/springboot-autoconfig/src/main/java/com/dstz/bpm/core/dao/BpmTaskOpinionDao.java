
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface BpmTaskOpinionDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.BpmTaskOpinion> {
    com.dstz.bpm.core.model.BpmTaskOpinion getByTaskId(java.lang.String s);

    java.util.List<com.dstz.bpm.core.model.BpmTaskOpinion> getByInstAndNode(@org.apache.ibatis.annotations.Param("instId") java.lang.String s, @org.apache.ibatis.annotations.Param("nodeId") java.lang.String s1, @org.apache.ibatis.annotations.Param("token") java.lang.String s2);

    void removeByInstId(@org.apache.ibatis.annotations.Param("instId") java.lang.String s);
}