package com.revature.sfadapter.services;


import com.revature.sfadapter.util.SFWSAccessObject;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

@Service
public class SFAuthService {

	private static final String clientId;
	private static final String clientSecret;
	private static final String authorizationUrl;
	private static final String myCallbackUrl;

	static{
		Map<String, String> env = System.getenv();
		clientId = env.get("CONSUMER_KEY");
		clientSecret = env.get("CONSUMER_SECRET");
		authorizationUrl = env.get("SF_AUTH_SERVER");		// *.saleforce.com/
		myCallbackUrl = env.get("CALLBACK_URL");
	}

	private static final Logger LOGGER = Logger.getLogger(SFAuthService.class);

	private String buildAuthorizationUrl(String endpoint, String clientRedirect){

		//StringBuilder url = new StringBuilder(String.format("%s?response_type=code", authorizationUrl));
		StringBuilder url = new StringBuilder(authorizationUrl.concat(endpoint));
		url.append("?response_type=code");
		url.append(String.format("&client_id=%s", clientId));
		url.append(String.format("&redirect_uri=%s", URLEncoder.encode(myCallbackUrl)));
		url.append(String.format("&state=%s", URLEncoder.encode(clientRedirect)));

		return url.toString();
	}

	private MultiValueMap<String, String> buildAccessRequestFormData(String accessCode){
		MultiValueMap<String, String> data = new LinkedMultiValueMap<>();

		data.add("grant_type", "authorization_code");
		data.add("code", accessCode);
		data.add("client_id", clientId);
		data.add("client_secret", clientSecret);
		data.add("redirect_uri",myCallbackUrl);

		return data;
	}

	public String getAuthUrl(String clientRedirect) throws IOException{
		LOGGER.debug("Building url for sales force request");
		String authUrl = buildAuthorizationUrl("/services/oauth2/authorize", clientRedirect);
		LOGGER.error("getAuthUrl is incomplete");
		return null;
	}

	public SFWSAccessObject sendForAccess(String accessCode){

		SFWSAccessObject accessObj;
		RestTemplate client = new RestTemplate();

		LOGGER.debug("Preparing request for Salesforce access token");
		LOGGER.debug("Building form data for request");
		MultiValueMap<String, String>data = buildAccessRequestFormData(accessCode);

		Assert.isTrue(data.size() > 0);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);
		System.out.println(request.toString());

		LOGGER.debug("Request ready sending to Salesforce");
		LOGGER.error("sendForAccess is incomplete");
		ResponseEntity<SFWSAccessObject> obj = client.postForEntity(authorizationUrl, request, SFWSAccessObject.class);
		return null;

	}
}
