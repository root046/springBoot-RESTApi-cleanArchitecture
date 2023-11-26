package com.bader88.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("dynamicFilter") // Apply filter when call bean and chose what want to be return
public class DynamicFiltering {
	
	private String value1;
	
	private String value2;
	
	private String value3;
	
	public DynamicFiltering(String value1, String value2, String value3) {
		super();
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}

	public String getValue3() {
		return value3;
	}

	@Override
	public String toString() {
		return "SomeBean [value1=" + value1 + ", value2=" + value2 + ", value3=" + value3 + "]";
	}
	

}
