package com.dstz.agilebpm.base.autoconfiguration;

import com.dstz.base.dao.annotation.SecondMapperAnnotation;
import com.dstz.base.dao.baseinterceptor.QueryInterceptor;
import com.dstz.base.dao.baseinterceptor.SaveInterceptor;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Description TODO
 * author hj
 * date 2020/9/28-11:17
 */
@Configuration
public class SecondDataSourceConfig {
    private static final String BasePackage = "com.dstz.**.dao";
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
    public PageInterceptor pageInterceptor() {
        final PageInterceptor pageInterceptor = new PageInterceptor();
        final Properties properties = new Properties();
        properties.setProperty("autoRuntimeDialect", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
    public QueryInterceptor queryInterceptor() {
        return new QueryInterceptor();
    }
//    @Primary
//    @Bean("mysqlSqlSessionFactory")
//    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(dynamicDataSource);
//        sqlSessionFactory.setMapperLocations(resolveMapperLocations("classpath*:mapper/*.xml"));
//        return sqlSessionFactory.getObject();
//    }

    @Bean(name = {"SecondSqlSessionFactoryBean"})
    public SqlSessionFactoryBean SecondSqlSessionFactoryBean(@Qualifier("dataSource2") DataSource dataSource, SaveInterceptor saveInterceptor) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations("classpath*:com/dstz/*/mapping/*.xml", "classpath*:com/dstz/*/*/mapping/*.xml"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor(), queryInterceptor(), saveInterceptor});
//        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
//        Properties mysqlp = new Properties();
//        mysqlp.setProperty("MySQL", "mysql");
//        mysqlp.setProperty("Oracle", "oracle");
//        databaseIdProvider.setProperties(mysqlp);
//        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        return sqlSessionFactoryBean;
    }
    @Bean({ "SecondMapperScannerConfigurer" })
    public MapperScannerConfigurer SecondMapperScannerConfigurer() {
        final MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("SecondSqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage(BasePackage);
        mapperScannerConfigurer.setAnnotationClass(SecondMapperAnnotation.class);
        return mapperScannerConfigurer;
    }
}
