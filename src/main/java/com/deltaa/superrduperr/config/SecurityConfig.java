package com.deltaa.superrduperr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for basic spring security
 * @author rajesh
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RestBasicAuthenticationEntryPoint restBasicAuthenticationEntryPoint;
	
	/**
	 * Overriding http security configurations
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
	    http.csrf().disable()
	    	.authorizeRequests().antMatchers("/").authenticated().and()
	    	.authorizeRequests().antMatchers("/h2console/**").permitAll()
	    	.and()
	    	.httpBasic().authenticationEntryPoint(restBasicAuthenticationEntryPoint);
	    http.headers().frameOptions().sameOrigin();
	    http.sessionManagement()
	    	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * Setting basic in-Memory user, password and role.
	 * @param authorizationManagerBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
	        .withUser("user")
	        .password(encoder.encode("password"))
	        .roles("USER");
	}
}