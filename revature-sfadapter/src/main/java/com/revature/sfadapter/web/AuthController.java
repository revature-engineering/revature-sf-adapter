package com.revature.sfadapter.web;

import com.revature.sfadapter.services.SFAuthService;
import com.revature.sfadapter.util.SFWSAccessObject;
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

	@Autowired
	public void setSfaService(SFAuthService sfaservice){
		Assert.notNull(sfaservice);
		this.sfaService = sfaservice;
	}
	
	@RequestMapping(value="/auth", method=RequestMethod.GET)
	public ResponseEntity<String> getAuth(@RequestParam(value="redirect_url") String redirectUrl, HttpServletResponse response){
		
		try{
			sfaService.sendForAuth(response, redirectUrl);
		}catch(IOException ex){
			return new ResponseEntity<>("An error has occurred. Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return null;
	}

	@RequestMapping(value = "/authenticate")
	public void finishAuth(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws IOException {



		SFWSAccessObject access = sfaService.sendForAccess(code);

		Assert.notNull(access);

		String sig = sfaService.saveToken(access);
		String url = state.substring(0, state.indexOf("?")+ 1);
		String qp = state.substring(state.indexOf("?") + 1);

		if(url.length() > 0) {
			url = url.concat(String.format("token=%s", sig));
			url = url.concat(String.format("&%s", qp));
		}else{
			url = state.concat(String.format("?token=%s", sig));
		}

		Assert.notNull(sig);
		response.sendRedirect(url);
	}
}
