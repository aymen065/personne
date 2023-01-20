package com.app.secTest.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.secTest.filters.JwtAuthentificationFilter;
import com.app.secTest.filters.JwtAuthorizationFilter;
import com.app.secTest.model.AppUser;
import com.app.secTest.services.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private AccountService accountService;
	
	
	public SecurityConfig(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(new UserDetailsService() {
			
			@Override
			//exécutée lors d'authentification
			public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
				AppUser appUser = accountService.loaduserByUserName(userName);
				Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				appUser.getAppRoles().forEach(r->{
					authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
				});
				return new User(appUser.getUserName(), appUser.getPassword(), authorities);
			}
		});
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		http.csrf().disable();
		//toutes les requètes ne nécessite pas une authentifiction
		http.authorizeRequests().anyRequest().permitAll();
		
		*/
		http.csrf().disable();
		//http.formLogin();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//http.authorizeRequests().antMatchers("/app/**").permitAll();
		//http.authorizeRequests().antMatchers(HttpMethod.POST ,"/app/createUser/**").hasAuthority("ADMIN");
		//http.authorizeRequests().antMatchers(HttpMethod.GET ,"/app/getAllUsers/**").hasAuthority("USER");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean()));
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
