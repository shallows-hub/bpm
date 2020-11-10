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
 * Description 不支持在pg里操作表,为了避免修改实际数据，只修改保留查询语句，其他作空处理
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

//    注意函数里将string类型的 id 转为 int类型了
    public List<Map<String, Object>> selectData(List<String> columnName, Map<String, Object> param) {
        StringBuilder sql = new StringBuilder();
        if (CollectionUtil.isEmpty(columnName)) {
            sql.append("SELECT * FROM " + table.getName());
        } else {
            sql.append("SELECT");
            for (String cn : columnName) {
                if (!sql.toString().endsWith("SELECT")) {
                    sql.append(",");
                }
                sql.append(" " + cn);
            }
            sql.append(" FROM " + table.getName());
        }

        sql.append(" WHERE ");

        List<Object> paramList = new ArrayList<>();// 参数
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            if (sql.toString().endsWith("?")) {
                sql.append(" and ");
            }

            if (entry.getKey().equals("id")){
                Object value = entry.getValue();
                String StringValue = (String)value;
                Object IntValue = Integer.valueOf(StringValue);
                entry.setValue(IntValue);
            }
            sql.append(entry.getKey() + " = ?");
            paramList.add(entry.getValue());
        }
        return jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
    }
}
