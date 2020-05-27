package org.springbee.jdbc.protection.autoconfigure;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springbee.jdbc.protection.interceptor.SqlProtectionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhanglei
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@AutoConfigureAfter(PageHelperAutoConfiguration.class) //must
@ConditionalOnProperty(value = "springbee.datasource.sql.protection.enabled")
public class SqlProtectionAutoConfiguration {

  @Autowired
  private List<SqlSessionFactory> sqlSessionFactoryList;

  @Value("${springbee.datasource.sql.protection.keywords}")
  public String[] keywords;

  @PostConstruct
  public void addDangerSqlInterceptor() {
    SqlProtectionInterceptor interceptor = new SqlProtectionInterceptor(keywords);
    Iterator it = this.sqlSessionFactoryList.iterator();
    while (it.hasNext()) {
      SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) it.next();
      sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }
  }
}
