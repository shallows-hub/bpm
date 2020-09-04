
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface BpmBusLinkDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.BpmBusLink> {
    java.util.List<com.dstz.bpm.core.model.BpmBusLink> getByInstanceId(java.lang.String s);
}