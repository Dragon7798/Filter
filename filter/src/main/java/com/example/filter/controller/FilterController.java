package com.example.filter.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

	
	@GetMapping("/response")
	public ResponseEntity<Object> show(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		
		return new ResponseEntity<>("Response ....",headers,HttpStatus.OK);
		
	}
}
