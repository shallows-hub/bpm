自动配置包  
说明：  
1、新增了 second dataSource  
1-1 使用方法
>须在配置文档配置 datasource2:  
spring:  
  datasource2:  
    driver-class-name: org.postgresql.Driver  
    url: jdbc:postgresql://localhost:5432/newoadb  
    username: ***  
    password: ***  
    dbType: postgresql/etc  
 
>在 form_cust_dialog 检查有哪些表是在第二数据源的，修改ds_key 和在 sys_data_source 添加数据源,如：  
    1，dataSource2，1，1，postgresql  
     
>对于Dao层，如果某些Dao层需要通过数据源2读取数据，需在interface添加 @SecondMapperAnnotation  

1-2 原理  
原系统有dynamic data source 逻辑，这逻辑会在wf模块读取数据时先在default
 data source 读取 form_cust_dialog 数据，根据ds_key来切换数据源，然后
 通过baseDao来查询。但dynamic data source逻辑在编写Aspect切换UserDao时失败，
 暂时通过配置多一个mybatis配置来解决，采用第二个配置的可添加 @SecondMapperAnnotation，
 原数据源的采用 @MapperAnnotation 