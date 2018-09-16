package com.oauth.authorized.resource.configuration.security;

import com.oauth.authorized.resource.configuration.ApplicationConfigurationProperties;
import com.oauth.authorized.resource.security.AccountAuthenticationProvider;
import com.oauth.authorized.resource.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


/**
 * Spring security configuration.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService userDetailsService;

	private final AccountAuthenticationProvider accountAuthenticationProvider;

	private final ApplicationConfigurationProperties properties;

	public WebSecurityConfiguration(
	        final CustomUserDetailsService userDetailsService,
            final AccountAuthenticationProvider accountAuthenticationProvider,
            final ApplicationConfigurationProperties properties) {

		this.userDetailsService = userDetailsService;
		this.accountAuthenticationProvider = accountAuthenticationProvider;
		this.properties = properties;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService);
		 auth.authenticationProvider(accountAuthenticationProvider);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(properties.getSigningKey());
		return jwtAccessTokenConverter;
	}


}
