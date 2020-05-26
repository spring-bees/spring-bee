package org.springbee.jdbc.protection.interceptor;

/**
 * @author zhanglei
 */
public enum SqlProtectionKeywords {

  ALTER("alter"),
  CREATEINDEX("createindex"),
  CREATETABLE("createtable"),
  CREATEVIEW("createview"),
  DELETE("delete"),
  DROP("drop"),
  EXECUTE("execute"),
  INSERT("insert"),
  MERGE("merge"),
  REPLACE("replace"),
  SELECT("select"),
  TRUNCATE("truncate"),
  UPDATE("update"),
  UPSERT("upsert"),
  NONE("none");

  private String type;

  private SqlProtectionKeywords(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
