package com.revature.sfadapter.services;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class SFAuthService {
	
	private String getRequestUrl(){
		String clientId = System.getenv("SF_CONSUMER_KEY");
		
		String url = "https://login.salesforce.com/services/oauth2/authorize?response_type=code";
		url += "&client_id=" + clientId;
		url += "&redirect_uri=" + URLEncoder.encode("https://dev.aduet.tech");
		
		return url;
	}
	
	public void sendForAuth(HttpServletResponse response) throws IOException{
		String url = getRequestUrl();
		
		response.sendRedirect(url);
	}

}
