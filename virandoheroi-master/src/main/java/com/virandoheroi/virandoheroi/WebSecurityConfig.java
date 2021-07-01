package com.virandoheroi.virandoheroi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario/cadastro_candidato").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario/cadastro_ong").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/cadastro_candidato").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/cadastro_ong").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario/cadastro_vaga").hasRole("ONG")
				.anyRequest()
				.authenticated()
			.and()
			.formLogin()
				.loginPage("/entrar")
				.permitAll()
			.and()
			.logout()
				.logoutSuccessUrl("/entrar?logout")
				.permitAll()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
	}
	
}
