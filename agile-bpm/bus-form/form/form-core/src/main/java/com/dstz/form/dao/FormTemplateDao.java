package com.dstz.form.dao;

import com.dstz.base.dao.annotation.MapperAnnotation;
import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.form.model.FormTemplate;
@MapperAnnotation
public interface FormTemplateDao extends BaseDao<String, FormTemplate> {
}
