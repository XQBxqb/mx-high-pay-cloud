package cn.high.mx.framework.druid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
@Configuration
public class DatasourceConfig {
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManagerOne( DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
