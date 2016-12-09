package com.revature.sfadapter.services;

import com.revature.sfadapter.data.services.DaoService;
import com.revature.sfadapter.util.SFWSAccessObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class SFAuthService {

	private DaoService<SFWSAccessObject, String> tokenService;

	private static final Logger LOGGER = Logger.getLogger(SFAuthService.class);
	@Autowired
	public void setTokenService(DaoService<SFWSAccessObject, String> tokenService){
		Assert.notNull(tokenService);
		this.tokenService = tokenService;
	}

	private String getRequestUrl(String clientRedirect){
		String clientId = System.getenv("SF_CONSUMER_KEY");

		String url = "https://test.salesforce.com/services/oauth2/authorize?response_type=code";
		url += "&client_id=" + clientId;
		url += "&redirect_uri=" + URLEncoder.encode("https://sf.aduet.tech/services/authenticate");
		url+= "&state=" + URLEncoder.encode(clientRedirect);
		url += "&prompt=login";

		return url;
	}

	public void sendForAuth(HttpServletResponse response, String clientRedirect) throws IOException{
		String url = getRequestUrl(clientRedirect);
		response.sendRedirect(url);
	}

	public SFWSAccessObject sendForAccess(String accessCode){
		System.out.println("Starting rest post to Salesforce");
		MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
		SFWSAccessObject accessObj;
		RestTemplate client = new RestTemplate();

		String clientId = System.getenv("SF_CONSUMER_KEY");
		String clientSecret = System.getenv("SF_CONSUMER_SECRET");

		data.add("grant_type", "authorization_code");
		data.add("code", accessCode);
		data.add("client_id", clientId);
		data.add("client_secret", clientSecret);
		data.add("redirect_uri","https://sf.aduet.tech/services/authenticate");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);
		System.out.println(request.toString());
		System.out.println("Post ready sending to Salesforce");
		ResponseEntity<SFWSAccessObject> obj = client.postForEntity("https://test.salesforce.com/services/oauth2/token", request, SFWSAccessObject.class);
		return obj.getBody();

	}

	public String saveToken(SFWSAccessObject token){
		return tokenService.saveOne(token).getSignature();
	}

}
