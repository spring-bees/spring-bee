package org.springbee.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class DataSourceAutoConfiguration implements EnvironmentAware {

  public static final String SPRING_DATASOURCE_PREFIX = "spring.datasource";
  public static final String HIKARI_PREFIX = ".hikari";
  private Environment environment;

  @Override
  public void setEnvironment(final Environment environment) {
    this.environment = environment;
  }

  @Bean(name = "defaultDataSource")
  public DataSource defaultDataSource() {
    return bindDataSource(SPRING_DATASOURCE_PREFIX);
  }

  private DataSource bindDataSource(String prefix) {
    DataSourceProperties properties = Binder.get(environment)
        .bind(prefix, DataSourceProperties.class).orElse(null);
    HikariConfig hikariConfig = Binder.get(environment)
        .bind(prefix + HIKARI_PREFIX, HikariConfig.class).orElseGet(null);
    hikariConfig.setJdbcUrl(properties.getUrl());
    hikariConfig.setUsername(properties.getUsername());
    hikariConfig.setPassword(properties.getPassword());
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public DatabaseIdProvider databaseIdProvider() {
    DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
    Properties p = new Properties();
    p.setProperty("Oracle", "oracle");
    p.setProperty("MySQL", "mysql");
    p.setProperty("H2", "h2");
    p.setProperty("SQLite", "sqlite");
    p.setProperty("SQL Server", "sqlserver");
    p.setProperty("DB2", "db2");
    p.setProperty("PostgreSQL", "postgresql");
    databaseIdProvider.setProperties(p);
    return databaseIdProvider;
  }
}