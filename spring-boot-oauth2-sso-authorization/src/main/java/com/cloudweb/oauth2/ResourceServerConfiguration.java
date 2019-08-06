package com.cloudweb.oauth2;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author kumar.panchal
 *
 */
@EnableResourceServer
@RestController
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@RequestMapping("/public")
	public String public0() {
		return "Public Response";
	}

	@RequestMapping("/private")
	public String privada() {
		return "Private Response";
	}

	@RequestMapping("/admin")
	public String admin() {
		return "Administrador Response";
	}
	
	@RequestMapping("/productservice")
	public String productService() {
		return "Product Response";
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**", "/public","/login/oauth2/code/template-style").permitAll();
		http.requestMatchers().antMatchers("/private","/productservice").and().authorizeRequests().antMatchers("/private","/productservice")
				.access("hasRole('USER')").and().requestMatchers().antMatchers("/admin").and().authorizeRequests()
				.antMatchers("/admin").access("hasRole('ADMIN')");
	}

}
