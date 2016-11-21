package com.revature.sfadapter.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.sfadapter.services.SFAuthService;

@RestController
@RequestMapping(value="/services")
public class AuthController {
	
	@Autowired
	SFAuthService sfaservice;
	
	@RequestMapping(value="/auth", method=RequestMethod.GET)
	public ResponseEntity<String> getAuth(@RequestParam(required=true, value="redirect_url") String redirectUrl, HttpServletResponse response){
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			sfaservice.sendForAuth(response);
		}catch(IOException ex){
			return new ResponseEntity<String>("An error has ocurred. Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return null;
	}

}
