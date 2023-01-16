package ar.com.tdm.codeDemostration.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MultiDBProperties {

	@Bean
    @ConfigurationProperties("spring.datasource.db1")
	@Primary
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.db2")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }
    
    @Bean
    public DataSource db1DataSource() {
        return db1DataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Bean
    public DataSource db2DataSource() {
        return db2DataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    
    @Bean
    public JdbcTemplate db1JdbcTemplate(@Qualifier("db1JdbcTemplate") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate db2JdbcTemplate(@Qualifier("db2JdbcTemplate") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
