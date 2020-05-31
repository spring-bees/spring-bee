package org.springbee.logging;

import lombok.Builder;

@Builder
public class RegexReplacement {

  public String regex;

  public String getRegex() {
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getReplacement() {
    return replacement;
  }

  public void setReplacement(String replacement) {
    this.replacement = replacement;
  }

  private String replacement;
}