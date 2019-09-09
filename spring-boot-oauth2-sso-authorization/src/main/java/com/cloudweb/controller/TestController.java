package com.cloudweb.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.codec.binary.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.unboundid.util.json.JSONException;
import com.unboundid.util.json.JSONObject;

@RestController
@RequestMapping("/Admin")
public class TestController {

	@GetMapping
	public ModelAndView GetToken(@RequestParam("code") String code) throws JsonProcessingException, IOException {
		ResponseEntity<String> response = null;
		System.out.println("Authorization Code------" + code);

		RestTemplate restTemplate = new RestTemplate();
		/*
		 * restTemplate.setMessageConverters(Arrays.asList(new
		 * StringHttpMessageConverter(), new FormHttpMessageConverter(), new
		 * ResourceHttpMessageConverter(), new ByteArrayHttpMessageConverter(), new
		 * MappingJackson2HttpMessageConverter() //new MultiMapHttpMessageConverter()
		 * 
		 * ));
		 */
		 
		
		// According OAuth documentation we need to send the client id and secret key in the header for authentication
		//String credentials = "consumerpoc:password";
		//String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
		String clientId = "consumerpoc";
		String clientSecret = "password";
		
		HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//headers.setCacheControl("no-cache");
	//	headers.setConnection("keep-alive");
		headers.set("Content-Type","application/x-www-form-urlencoded");
	
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		
		
		/*
		 * map.add("client_id",clientId); map.add("client_secret",clientSecret);
		 * map.add("grant_type","authorization_code"); map.add("code",code);
		 * map.add("redirect_uri", "http://localhost:8084/Admin/ValiateToken");
		 */

final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map ,
        headers);
	    
		String access_token_url = "http://localhost:8080/openam/oauth2/realms/root/realms/Test/access_token";
		access_token_url += "?grant_type=authorization_code";
		access_token_url += "&code=" + code;
		access_token_url += "&client_id="+clientId;
		access_token_url += "&client_secret="+clientSecret;
		access_token_url += "&redirect_uri=http://localhost:8084/Admin/ValiateToken";

		
		try {	
		ResponseEntity<String> responseEntity = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
			
		JSONObject jsonObject = null;
		
		  if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
		        try {
		            jsonObject = new JSONObject(responseEntity.getBody());
		        } catch (JSONException e) {
		            throw new RuntimeException("JSONException occurred");
		        }
		    }
		  } catch (final HttpClientErrorException httpClientErrorException) {
			  httpClientErrorException.printStackTrace();
		  } catch (HttpServerErrorException httpServerErrorException) {
		         httpServerErrorException.printStackTrace();
		  } catch (Exception exception) {
		        exception.printStackTrace();
		    } 
	
				
		
			
		
		return null;
	}
	
	
	

	@GetMapping("/ValidateToken")
	public ModelAndView ValidateToken(@RequestParam("idToken") String id_token) {
		ResponseEntity<String> response = null;
		System.out.println("JWT Access Token------" + id_token);

		RestTemplate restTemplate = new RestTemplate();

		// According OAuth documentation we need to send the client id and secret key in the header for authentication
		String credentials = "consumerpoc:password";
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
		String clientId = "consumerpoc";
		String clientSecret = "password";
		
		HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Arrays.asList(MediaType.valueOf("*/*")));
		headers.add("Authorization", "Basic " + encodedCredentials);
	//	headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded"));
		
		//setting headers
		headers.set("Content-Type","application/x-www-form-urlencoded");
		headers.set("Accept","*/*");
		headers.set("Cache-Control","no-cache");
		
		
		
		
		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_validate_url = "http://localhost:8080/openam/oauth2/realms/root/realms/Test/introspect";
		access_token_validate_url += "?client_id="+clientId;
		access_token_validate_url += "&client_secret="+clientSecret;
		access_token_validate_url += "&token=" + id_token;
		

		response = restTemplate.exchange(access_token_validate_url, HttpMethod.POST, request, String.class);
				
		System.out.println("Access Token  Validate Response ---------" + response.getBody());

		return null;
	}
	
	
}
