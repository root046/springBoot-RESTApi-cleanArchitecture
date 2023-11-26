package com.bader88.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping(path = "/static-filtering")
	public StaticFiltering staticFiltreing() {
		return new StaticFiltering("value1","value2","value3");// filter will be applied in(StaticFiltering.java)bean->(static filter)
	}
	
	@GetMapping(path = "/static-filtering-list")
	public List<StaticFiltering> staticFiltreingList() {
		return Arrays.asList( new StaticFiltering("value1","value2","value3")
							,new StaticFiltering("value1","value2","value3"));
	}
	
	@GetMapping(path = "/dynamic-filtering")
	public MappingJacksonValue dynamicFiltreing() {
		
		DynamicFiltering dynamicFiltering = new DynamicFiltering("value1","value2","value3");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dynamicFiltering);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("value1");// Apply filter here,and choose what properties will return->(dynamic filter)
		FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", filter );
		
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue; 
		
	}
	
	@GetMapping(path = "/dynamic-filtering-list")
	public MappingJacksonValue dynamicFiltreingList() {
		List<DynamicFiltering> list = Arrays.asList( new DynamicFiltering("value1","value2","value3")
							,new DynamicFiltering("value1","value2","value3"));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("value1","value3");// we can choose more than one properties
		FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", filter );
		
		mappingJacksonValue.setFilters(filters);

		
		
		return mappingJacksonValue;
	}
	
	
	

}
