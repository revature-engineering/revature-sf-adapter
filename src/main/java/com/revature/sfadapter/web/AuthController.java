package com.revature.sfadapter.web;

import com.revature.sfadapter.services.SFAuthService;
import com.revature.sfadapter.util.SFWSAccessObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that provides access to authentication and authorization
 * functionality
 */
@RestController
@RequestMapping(value="/services")
public class AuthController {

	private SFAuthService sfaService;
	private static final Logger LOGGER = Logger.getLogger(AuthController.class);

	@Autowired
	public void setSfaService(SFAuthService sfaservice){
		Assert.notNull(sfaservice);
		this.sfaService = sfaservice;
	}

	@RequestMapping(value="/authorize", method=RequestMethod.GET)
	public ResponseEntity<String> getAuth(@RequestParam(value="redirect_url") String redirectUrl, HttpServletResponse response){

		try{
			sfaService.getAuthUrl(redirectUrl);
		}catch(IOException ex){
			return new ResponseEntity<>("An error has occurred. Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

	@RequestMapping(value = "/salesforcecallback")
	public void finishAuth(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws IOException {

		LOGGER.error("This method is incomplete");
	}
}
