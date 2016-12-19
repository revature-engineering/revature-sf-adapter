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
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class SFAuthService {

	private DaoService<SFWSAccessObject, String> tokenService;
	private static final String clientId;
	private static final String clientSecret;
	private static final String authorizationUrl;
	private static final String myCallbackUrl;

	static{
		Map<String, String> env = System.getenv();
		clientId = env.get("SF_CONSUMER_KEY");
		clientSecret = env.get("SF_CONSUMER_SECRET");
		authorizationUrl = env.get("SF_OAUTH_URL");		// *.saleforce.com/
		myCallbackUrl = env.get("SF_REG_REDIRECT");
	}

	private static final Logger LOGGER = Logger.getLogger(SFAuthService.class);
	@Autowired
	public void setTokenService(DaoService<SFWSAccessObject, String> tokenService){
		Assert.notNull(tokenService);
		this.tokenService = tokenService;
	}

	private String buildRequestUrl(String endpoint, String clientRedirect){

		StringBuilder url = new StringBuilder(String.format("%s?response_type=code", authorizationUrl));
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
		//ResponseEntity<SFWSAccessObject> obj = client.postForEntity(, request, SFWSAccessObject.class);
		return null;

	}

	public String saveToken(SFWSAccessObject token){
		return tokenService.saveOne(token).getSignature();
	}

}
