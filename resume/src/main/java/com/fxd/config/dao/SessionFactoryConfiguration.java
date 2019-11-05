package com.fxd.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SessionFactoryConfiguration {
    //mybatis-config.xml配置文件的路径
    private String mybatisConfigFile;

    @Value("${mybatisConfigFile}")
    public void setMybatisConfigFile(String mybatisConfigFile) {
        this.mybatisConfigFile = mybatisConfigFile;
    }

    //mybatis mapper文件所在的路径
    private String mapperLocations;

    @Value("${mapperLocations}")
    public void setMapperPath(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    //mybatis扫描实体类的package
    @Value("${typeAliasesPackage}")
    private String typeAliasesPackage;
    @Autowired
    private DataSource dataSource;

    /**
     * 配置SqlSessionFactory 实例 并设置config设置mapper映射路径 设置datasource数据源
     *
     * @return
     * @throws IOException
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSFB = new SqlSessionFactoryBean();

        try {

            //配置数据源
            sqlSFB.setDataSource(dataSource);

            //配置MyBatis全局配置文件：mybatis-config.xml
            sqlSFB.setConfigLocation(new ClassPathResource(mybatisConfigFile));

            //扫描sql配置文件：mapper需要的xml文件
            ResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();
            sqlSFB.setMapperLocations(resolver.getResources(mapperLocations));

            //扫描entity包 使用别名
            sqlSFB.setTypeAliasesPackage(typeAliasesPackage);

        } catch (Exception e) {
            System.out.println("创建SqlSession连接工厂错误：" + e.getMessage());
        }
        return sqlSFB;
    }
}
