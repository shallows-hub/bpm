package com.dstz.base.db.dboper;

import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description pg的operator，注意实际使用时pg区分大小写，但原生的代码里对mysql 不区分大小写，所以代码有区别
 * author hj
 * date 2020/9/30-16:34
 */
public class PostgreDbOperator extends DbOperator {
    /**
     * @param jdbcTemplate
     */
    public PostgreDbOperator(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public String type() {
        return DbType.POSTGRESQL.getKey();
    }

    @Override
    public Map<String, String> getTableNames(String tableName) {
        String sql = "select tablename,tableowner from pg_tables t where t.schemaname='public'";
        List<Map<String, Object>> list;
        if (StringUtils.isNotEmpty(tableName)) {
            sql += " AND tablename LIKE ?";
            list = jdbcTemplate.queryForList(sql, "%" + tableName + "%");
        } else {
            list = jdbcTemplate.queryForList(sql);
        }

        Map<String, String> map = new LinkedHashMap<>();
        for (Map<String, Object> m : list) {
            map.put(m.get("tablename").toString(), m.get("tableowner").toString());
        }

        return map;
    }

    @Override

    public List<String> getViewNames(String viewName) {
        String sql = "SELECT * FROM pg_views ";
        if (StringUtils.isNotEmpty(viewName))
            sql += " WHERE viewname LIKE ?";
        List<String> list = new ArrayList<>();
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, "%" + viewName.toUpperCase() + "%");
        for (Map<String, Object> line : results) {
            list.add(line.get("viewname").toString());
        }
        return list;
    }

    @Override
    public Table<Column> getTable(String tableName) {
        Table<Column> table = new Table<>();
        Map<String, String> tableNames = getTableNames(tableName);
        if (tableNames.isEmpty()) {
            throw new BusinessException(String.format("根据表名[%s]获取不到表", tableName.toUpperCase()));
        }
        table.setName(tableName);
        table.setComment(tableNames.get(tableName));
        table.setColumns(getColumns(tableName));

        return table;
    }
    @Override
    public Table<Column> getView(String viewName) {
        return null;
    }
    private List<Column> getColumns(String name) {
        String sql = "select\n" +
                "col.table_name,\n" +
                "col.ordinal_position,\n" +
                "col.column_name,\n" +
                "col.data_type,\n" +
                "col.character_maximum_length,\n" +
                "col.numeric_precision,\n" +
                "col.numeric_scale,\n" +
                "col.is_nullable,\n" +
                "col.column_default,\n" +
                "des.description\n" +
                "from\n" +
                "information_schema.columns col left join pg_description des on\n" +
                "col.table_name::regclass = des.objoid\n" +
                "and col.ordinal_position = des.objsubid\n" +
                "where\n" +
                "table_schema = 'public'\n" +
                "and table_name = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, name);
        List<Column> columns = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Column column = new Column();
            column.setComment(getOrDefault(map, "description", "").toString());
            column.setDefaultValue(map.get("column_default") == null ? null : map.get("column_default").toString());
            column.setName(getOrDefault(map, "column_name", "").toString());
            column.setPrimary("PRI".equals(getOrDefault(map, "COLUMN_KEY", "")));
            column.setRequired("NO".equals(getOrDefault(map, "is_nullable", "")));
            column.setType(ColumnType.getByDbDataType(map.get("data_type").toString(),"字段["+column.getComment()+"("+column.getName()+")]").getKey());
            if (ColumnType.VARCHAR.equalsWithKey(column.getType())) {
                column.setLength(Integer.parseInt(getOrDefault(map, "character_maximum_length", "0").toString()));
            }
            if (ColumnType.NUMBER.equalsWithKey(column.getType())) {
                column.setLength(Integer.parseInt(getOrDefault(map, "numeric_precision", "0").toString()));
                column.setDecimal(Integer.parseInt(getOrDefault(map, "numeric_scale", "0").toString()));
            }
            columns.add(column);
        }
        return columns;
    }
    /*
    * postgre数据库暂时不需要分区
    * */
    @Override
    public boolean supportPartition(String tableName) {
        return false;
    }

    @Override
    public boolean isExsitPartition(String tableName, String partName) {
        return false;
    }

    @Override
    public void createPartition(String tableName, String partName) {

    }
}
