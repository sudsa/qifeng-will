package com.hanxiaozhang.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.hanxiaozhang.enums.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态切换数据源配置类〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Slf4j
@MapperScan(basePackages = "com.hanxiaozhang.*.dao")
@Configuration
public class DynamicDataSourceConfig {


    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.master")
    public DruidXADataSource dbMasterXa() {
        return new DruidXADataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.slave1")
    public DruidXADataSource dbSlave1Xa() {
        return new DruidXADataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.slave2")
    public DruidXADataSource dbSlave2Xa() {
        return new DruidXADataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.other")
    public DruidXADataSource dbOtherXa() {
        return new DruidXADataSource();
    }

    @Bean
    public DataSource dbMaster() {
        log.info("Initialized -> [{}]", "DataSource DB_Master Start");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName(DataSourceKey.DB_MASTER.toString());
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        xaDataSource.setXaDataSource(dbMasterXa());
        xaDataSource.setPoolSize(5);
        return xaDataSource;
    }
    @Bean
    public DataSource dbSlave1() {
        log.info("Initialized -> [{}]", "DataSource DB_Slave1 Start");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName(DataSourceKey.DB_SLAVE1.toString());
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        xaDataSource.setXaDataSource(dbSlave1Xa());
        xaDataSource.setPoolSize(5);
        return xaDataSource;
    }
    @Bean
    public DataSource dbSlave2() {
        log.info("Initialized -> [{}]", "DataSource DB_Slave2 Start");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName(DataSourceKey.DB_SLAVE2.toString());
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        xaDataSource.setXaDataSource(dbSlave2Xa());
        xaDataSource.setPoolSize(5);
        return xaDataSource;
    }

    @Bean
    public DataSource dbOther() {
        log.info("Initialized -> [{}]", "DataSource DB_Other Start");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName(DataSourceKey.DB_OTHER.toString());
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        xaDataSource.setXaDataSource(dbOtherXa());
        xaDataSource.setPoolSize(5);
        return xaDataSource;
    }


    /**
     * 核心动态数据源
     *
     * @return 数据源实例
     */
    @Bean
    public DataSource dynamicDataSource() {
        log.info("Initialized -> [{}]", "DynamicDataSource Start");
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        //配置默认目标数据源
        dataSource.setDefaultTargetDataSource(dbMaster());

        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put(DataSourceKey.DB_MASTER, dbMaster());
        dataSourceMap.put(DataSourceKey.DB_SLAVE1, dbSlave1());
        dataSourceMap.put(DataSourceKey.DB_SLAVE2, dbSlave2());
        dataSourceMap.put(DataSourceKey.DB_OTHER, dbOther());
        //配置目标数据源Map
        dataSource.setTargetDataSources(dataSourceMap);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        log.info("Initialized -> [{}]", "SqlSessionFactory Start");
        SqlSessionFactoryBean sqlSessionFactoryBean = new  SqlSessionFactoryBean();
        //配置数据源
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //解决找不到mapper文件的问题
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/**/*Mapper.xml"));

        org.apache.ibatis.session.Configuration configuration =new  org.apache.ibatis.session.Configuration();
        //配置驼峰映射
        configuration.setMapUnderscoreToCamelCase(true);
        //配置打印SQL日志，yml中logging以配置打印com.hanxiaozhang包下所有日志
        //configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        //配置扫描通配符包的路径，可以配置自动扫描包路径给类配置别名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.hanxiaozhang.**.domain");

        //配置事务
        sqlSessionFactoryBean.setTransactionFactory(new DynamicDataSourceTransactionFactory());

        return sqlSessionFactoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        log.info("Initialized -> [{}]", "SqlSessionTemplate Start");
        return new SqlSessionTemplate(sqlSessionFactory());
    }


    /**
     * JtaTransactionManager（分布式事务使用）
     * @return
     * @throws SystemException
     */
    @Bean
    public JtaTransactionManager jtaTransactionManager() throws SystemException {
        log.info("Initialized -> [{}]", "JtaTransactionManager Start");
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransactionImp());
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }

    private UserTransactionManager userTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    private UserTransactionImp userTransactionImp() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(5000);
        return userTransactionImp;
    }


}
