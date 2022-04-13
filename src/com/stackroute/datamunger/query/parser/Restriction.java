package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * generate getter and setter for this class,
 * Also override toString method
 * */

public class Restriction {

	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	private String value;
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}

	private String condition;
	public String getCondition() {return condition;}
	public void setCondition(String condition) {this.condition = condition;}

	// Write logic for constructor
	public Restriction(String name, String value, String condition) {
		this.name = name;
		this.value = value;
		this.condition = condition;
	}

	public Restriction(){}

	@Override
	public String toString() {
		return "Restriction{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				", condition='" + condition + '\'' +
				'}';
	}

}