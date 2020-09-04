
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface BpmTaskStackDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.BpmTaskStack> {
    com.dstz.bpm.core.model.BpmTaskStack getByTaskId(java.lang.String s);

    void removeByInstanceId(java.lang.String s);

    java.util.List<com.dstz.bpm.core.model.BpmTaskStack> getByInstanceId(java.lang.String s);
}