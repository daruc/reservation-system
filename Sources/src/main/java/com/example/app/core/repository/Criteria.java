package com.example.app.core.repository;

public class Criteria {
	  protected SqlOperator operator;
	  protected String field;
	  protected Object value;
	  
	  public Criteria(String field, SqlOperator operator, Object value) {
		  this.field = field;
		  this.operator = operator;
		  this.value = "'" + value + "'";
	  }
	  
	  public static enum SqlOperator {
		  EQUAL("=");
		  
		  private String strOperator;
		  
		  SqlOperator(String strOperator) {
			  this.strOperator = strOperator;
		  }
		  
		  @Override
		  public String toString() {
			  return strOperator;
		  }
	  }
	  
	  public String toSql(DataMap dataMap) {
		  return "(" + dataMap.field2Column(field) + " " + operator + " " + value.toString() + ")";
	  }
}
