package com.dstz.bpm.core.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;

@MapperAnnotation
public interface BpmDefinitionDao extends com.dstz.base.dao.BaseDao<java.lang.String,com.dstz.bpm.core.model.BpmDefinition> {
    com.dstz.bpm.core.model.BpmDefinition getMainByDefKey(java.lang.String s);

    void updateActResourceEntity(@org.apache.ibatis.annotations.Param("deploymentId") java.lang.String s, @org.apache.ibatis.annotations.Param("resName") java.lang.String s1, @org.apache.ibatis.annotations.Param("bpmnBytes") byte[] bytes);

    java.util.List<com.dstz.bpm.core.model.BpmDefinition> getByKey(java.lang.String s);

    java.util.List<com.dstz.bpm.core.model.BpmDefinition> getDefByActModelId(java.lang.String s);

    com.dstz.bpm.core.model.BpmDefinition getByActDefId(java.lang.String s);

    void updateToMain(java.lang.String s);

    java.util.List<com.dstz.bpm.core.model.BpmDefinition> getMyDefinitionList(com.dstz.base.api.query.QueryFilter queryFilter);

    void updateForMainVersion(@org.apache.ibatis.annotations.Param("mainDefId") java.lang.String s, @org.apache.ibatis.annotations.Param("key") java.lang.String s1);
}