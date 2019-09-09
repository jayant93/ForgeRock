package com.cloudweb.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
/**
 * 
 * @author kumar.panchal
 *
 */
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
	
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();
		
		UserDetails demo = User.builder().username("demo@g.com").password(passwordEncoder().encode("kumar@1801")).roles("USER")
				.build();
		
		UserDetails kumar = User.builder().username("cloudweb").password(passwordEncoder().encode("cloudweb")).roles("USER")
				.build();
		
		UserDetails userAdmin = User.builder().username("admin").password(passwordEncoder().encode("pass"))
				.roles("ADMIN").build();
		
		return new InMemoryUserDetailsManager(user, userAdmin,demo,kumar);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().httpBasic().disable().authorizeRequests().antMatchers("/", "/index", "/webpublic")
				.permitAll()
				.antMatchers("/webprivate").authenticated().antMatchers("/webadmin").hasRole("ADMIN").and()
				.formLogin().loginPage("/login").permitAll().and().logout().permitAll().and().oauth2Login();
	}
}
