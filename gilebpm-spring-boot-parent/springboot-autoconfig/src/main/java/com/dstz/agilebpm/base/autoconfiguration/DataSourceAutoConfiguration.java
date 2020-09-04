package com.dstz.agilebpm.base.autoconfiguration;

import com.alibaba.druid.pool.DruidDataSource;
import com.dstz.base.dao.annotation.MapperAnnotation;
import com.dstz.base.dao.baseinterceptor.QueryInterceptor;
import com.dstz.base.dao.baseinterceptor.SaveInterceptor;
import com.dstz.base.db.datasource.DynamicDataSource;
import com.github.pagehelper.PageInterceptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import com.dstz.agilebpm.base.autoconfiguration.DataSourceExtraProperties;

@EnableConfigurationProperties({ DataSourceExtraProperties.class })
@Configuration
public class DataSourceAutoConfiguration
{
    private static final String BasePackage = "com.dstz.**.dao";
    @Primary
    @ConditionalOnClass({ DruidDataSource.class })
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSourceDefault(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }

    @Bean
    public DynamicDataSource dataSource(DataSourceExtraProperties dataSourceExtraProperties, DataSource dataSourceDefault) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(1);
        targetDataSources.put("dataSourceDefault", dataSourceDefault);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultDbtype(dataSourceExtraProperties.getDbType());
        return dynamicDataSource;
    }

    @Bean(name = { "jdbcTemplate" })
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public PageInterceptor pageInterceptor() {
        final PageInterceptor pageInterceptor = new PageInterceptor();
        final Properties properties = new Properties();
        properties.setProperty("autoRuntimeDialect", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    public QueryInterceptor queryInterceptor() {
        return new QueryInterceptor();
    }

    @Bean
    public SaveInterceptor saveInterceptor() {
        return new SaveInterceptor();
    }

    @Bean(name = {"abSqlSessionFactory"})
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, SaveInterceptor saveInterceptor) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations("classpath*:com/dstz/*/mapping/*.xml", "classpath*:com/dstz/*/*/mapping/*.xml"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor(), queryInterceptor(), saveInterceptor});
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties mysqlp = new Properties();
        mysqlp.setProperty("MySQL", "mysql");
        mysqlp.setProperty("Oracle", "oracle");
        databaseIdProvider.setProperties(mysqlp);
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        return sqlSessionFactoryBean;
    }

    private Resource[] resolveMapperLocations(String... locations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        for (String mapperLocation : locations) {
            try {
                resources.addAll(Arrays.asList(resourceResolver.getResources(mapperLocation)));
            } catch (IOException e) {
            }
        }
        return (Resource[]) resources.toArray(new Resource[resources.size()]);
    }

    @Bean({ "abMapperScannerConfigurer" })
    public MapperScannerConfigurer mapperScannerConfigurer() {
        final MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("abSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(BasePackage);
        mapperScannerConfigurer.setAnnotationClass(MapperAnnotation.class);
        return mapperScannerConfigurer;
    }

    @Bean({ "abSqlSessionTemplate" })
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("abSqlSessionFactory") final SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory.getObject());
    }
}
