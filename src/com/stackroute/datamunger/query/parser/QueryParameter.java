package com.stackroute.datamunger.query.parser;

import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

//obj rep for the query we process
//here we give obj rep for the query

//contain dif properties

public class QueryParameter {

	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {this.fileName = fileName;}

	private String baseQuery;
	public String getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(String baseQuery) {this.baseQuery = baseQuery;}

	private List<Restriction> restrictions;
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(List<Restriction> restrictions) {this.restrictions = restrictions;}

	//----------------------------------
	public List<String> getLogicalOperators() {
		return null;
	}

	private List<String> fields;
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {this.fields = fields;}

	//----------------------------------
	public List<AggregateFunction> getAggregateFunctions() {
		return null;
	}

	private List<String> groupByFields;
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	public void setGroupByFields(List<String> groupByFields){this.groupByFields = groupByFields;}

	private List<String> orderByFields;
	public List<String> getOrderByFields() {
		return orderByFields;
	}
	public void setOrderByFields(List<String> orderByFields){this.orderByFields = orderByFields;}
}