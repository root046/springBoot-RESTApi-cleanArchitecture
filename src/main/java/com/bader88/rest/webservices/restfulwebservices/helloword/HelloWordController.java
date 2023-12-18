package com.bader88.rest.webservices.restfulwebservices.helloword;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
public class HelloWordController {
	private MessageSource messageSource;
	
	public HelloWordController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping(path="/hello-word")
	public String helloword() {
		return "hello word...";
	}
	
	@GetMapping(path="/hello-word-bean")
	public HelloWordBean hellowordBean() {
		return new HelloWordBean("hello word Bean...");
	}
	
	@GetMapping(path = "/hello-word/path-variable/{name}")
	public HelloWordBean helloWordPathVariable(@PathVariable String name) {
		return new HelloWordBean(String.format("hello word : %s", name));
	}
	
	
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale );
		
		//return "Hello World V2"; 
		
		//1:
		//2:
//		- Example: `en` - English (Good Morning)
//		- Example: `ar` - Arabic (صباح الخير)

	}
}
