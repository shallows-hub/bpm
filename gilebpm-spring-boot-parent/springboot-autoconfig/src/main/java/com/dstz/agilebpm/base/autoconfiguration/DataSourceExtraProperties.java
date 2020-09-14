package com.dstz.agilebpm.base.autoconfiguration;

/**
 * Description TODO
 * author hj
 * date 2020/9/11-16:57
 */

import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceExtraProperties
{
    private String dbType;

    public String getDbType() {
        return this.dbType;
    }

    public void setDbType(final String dbType) {
        this.dbType = dbType;
    }
}
