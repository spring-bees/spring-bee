package org.springbee.jdbc.protection.interceptor;

import java.io.StringReader;
import java.sql.Connection;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.upsert.Upsert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springbee.springbee.common.exception.DangerException;

/**
 * Detect danger keywords in SQL
 *
 * @author zhanglei
 */
@Slf4j
@Intercepts(value = @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}))
public class SqlProtectionInterceptor implements Interceptor {

  private String[] keywords;

  public SqlProtectionInterceptor(String[] keywords) {
    this.keywords = keywords;
  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    StatementHandler handler = (StatementHandler) invocation.getTarget();
    BoundSql boundSql = handler.getBoundSql();
    String sql = boundSql.getSql();
    if (keywords != null) {
      String sqltype = getSqlType(sql).getType();
      for (String dangerType : keywords) {
        if (dangerType.toLowerCase().equals(sqltype)) {
          MetaObject statementHandler = SystemMetaObject.forObject(handler);
          MappedStatement mappedStatement = (MappedStatement) statementHandler
              .getValue("delegate.mappedStatement");
          log.error("Danger SQL keyword [{}] detected from [{}={}] "
              + "You can set springbee.sql.danger.enabled=false to disable SQL threat protection "
              + "or remove keyword [{}] from springbee.sql.danger.type", dangerType, mappedStatement.getId(), sql, dangerType);
          throw new DangerException();
        }
      }
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {

  }

  public static SqlProtectionKeywords getSqlType(String sql)
      throws JSQLParserException {
    Statement sqlStmt = CCJSqlParserUtil.parse(new StringReader(sql));
    if (sqlStmt instanceof Alter) {
      return SqlProtectionKeywords.ALTER;
    } else if (sqlStmt instanceof CreateIndex) {
      return SqlProtectionKeywords.CREATEINDEX;
    } else if (sqlStmt instanceof CreateTable) {
      return SqlProtectionKeywords.CREATETABLE;
    } else if (sqlStmt instanceof CreateView) {
      return SqlProtectionKeywords.CREATEVIEW;
    } else if (sqlStmt instanceof Delete) {
      return SqlProtectionKeywords.DELETE;
    } else if (sqlStmt instanceof Drop) {
      return SqlProtectionKeywords.DROP;
    } else if (sqlStmt instanceof Execute) {
      return SqlProtectionKeywords.EXECUTE;
    } else if (sqlStmt instanceof Insert) {
      return SqlProtectionKeywords.INSERT;
    } else if (sqlStmt instanceof Merge) {
      return SqlProtectionKeywords.MERGE;
    } else if (sqlStmt instanceof Replace) {
      return SqlProtectionKeywords.REPLACE;
    } else if (sqlStmt instanceof Select) {
      return SqlProtectionKeywords.SELECT;
    } else if (sqlStmt instanceof Truncate) {
      return SqlProtectionKeywords.TRUNCATE;
    } else if (sqlStmt instanceof Update) {
      return SqlProtectionKeywords.UPDATE;
    } else if (sqlStmt instanceof Upsert) {
      return SqlProtectionKeywords.UPSERT;
    } else {
      return SqlProtectionKeywords.NONE;
    }
  }

}
