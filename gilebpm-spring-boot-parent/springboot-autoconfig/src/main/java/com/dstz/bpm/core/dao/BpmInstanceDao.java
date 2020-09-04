
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface BpmInstanceDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.BpmInstance> {
    java.util.List<com.dstz.bpm.core.model.BpmInstance> getApplyList(com.dstz.base.api.query.QueryFilter queryFilter);

    java.util.List<com.dstz.bpm.core.model.BpmTaskApprove> getApproveHistoryList(com.dstz.base.api.query.QueryFilter queryFilter);

    java.util.List<com.dstz.bpm.core.model.BpmInstance> getByPId(@org.apache.ibatis.annotations.Param("parentInstId") java.lang.String s);

    com.dstz.bpm.core.model.BpmInstance getByActInstId(@org.apache.ibatis.annotations.Param("actInstId") java.lang.String s);
}