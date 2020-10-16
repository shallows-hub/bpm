package com.dstz.base.db.tableoper;

import cn.hutool.core.collection.CollectionUtil;
import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.dboper.DbOperator;
import com.dstz.base.db.dboper.DbOperatorFactory;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * Description 不支持在pg里操作表
 * author hj
 * date 2020/10/13-10:07
 */
public class PostgreTableOperator extends TableOperator {
    public PostgreTableOperator(Table<? extends Column> table, JdbcTemplate jdbcTemplate) {
        super(table, jdbcTemplate);
    }
    @Override
    public String type() {
        return DbType.POSTGRESQL.getKey();
    }
    public void createTable() { }
    public void dropTable() { }
    public boolean isTableCreated() {
        return true;
    }
    public void addColumn(Column column) { }
    public void updateColumn(Column column) { }
    public void dropColumn(String columnName) { }
    public void insertData(Map<String, Object> data) { }
    public void deleteData(Object id) { }
    public void deleteData(Map<String, Object> param) { }
    public void updateData(Map<String, Object> data) { }
//    public Map<String, Object> selectData(List<String> columnName, Object id) {
//        Map<String, Object> param = new HashMap<>();
//        param.put(table.getPkColumn().getName(), id);
//        List<Map<String, Object>> list = selectData(columnName, param);
//        if (!list.isEmpty()) {
//            return list.get(0);
//        }
//        return null;
//    }
//
    public Map<String, Object> selectData(Object id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id",0);
        return param;
//        param.put(table.getPkColumn().getName(), id);
//        List<Map<String, Object>> list = selectData(param);
//        if (!list.isEmpty()) {
//            return list.get(0);
//        }
//        return null;
    }
//
//    public List<Map<String, Object>> selectData(Map<String, Object> param) {
//        return selectData(null, param);
//    }
//
//    public List<Map<String, Object>> selectData(List<String> columnName, Map<String, Object> param) {
//        StringBuilder sql = new StringBuilder();
//        if (CollectionUtil.isEmpty(columnName)) {
//            sql.append("SELECT * FROM " + table.getName());
//        } else {
//            sql.append("SELECT");
//            for (String cn : columnName) {
//                if (!sql.toString().endsWith("SELECT")) {
//                    sql.append(",");
//                }
//                sql.append(" " + cn);
//            }
//            sql.append(" FROM " + table.getName());
//        }
//
//        sql.append(" WHERE id = 0");

//        List<Object> paramList = new ArrayList<>();// 参数
//        for (Map.Entry<String, Object> entry : param.entrySet()) {
//            if (sql.toString().endsWith("?")) {
//                sql.append(" and ");
//            }
//            sql.append(entry.getKey() + " = ?");
//            paramList.add(entry.getValue());
//        }

//        return jdbcTemplate.queryForList(sql.toString());
//    }

//    public void syncColumn() {
//        // 未生成表，不处理
//        if (!isTableCreated()) {
//            return;
//        }
//        Set<String> dbColumnNames = new HashSet<>();// 数据库中存在的字段名
//        Table<Column> dbTable = getDbTable();
//        for (Column c : dbTable.getColumns()) {
//            dbColumnNames.add(c.getName());
//        }
//
//        for (String columnName : dbColumnNames) {
//            if (this.table.getColumn(columnName) == null) {// 数据库表内有，但是结构没有，删除
//                dropColumn(columnName);
//            }
//        }
//
//        for (Column column : table.getColumns()) {
//            boolean exits = false;
//            for (String columnName : dbColumnNames) {
//                if (columnName.equalsIgnoreCase(column.getName())) {
//                    exits = true;
//                    break;
//                }
//            }
//            if (!exits) {// 结构有，数据库表内没有，增加
//                addColumn(column);
//            } else if (!dbTable.getColumn(column.getName()).equals(column)) {
//                updateColumn(column);// 更新一遍结构
//            }
//        }
//    }
//
//    public Table<Column> getDbTable() {
//        DbOperator dbOperator = DbOperatorFactory.newOperator(type(), jdbcTemplate);
//        return dbOperator.getTable(table.getName());
//    }
}
