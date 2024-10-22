package co.edu.usco.pw.loginSpringSecurityBasic.configuration;

import co.edu.usco.pw.loginSpringSecurityBasic.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.authorizeHttpRequests(request -> {
					request.requestMatchers("/loginForm", "/css/**", "/js/**").permitAll()
							.requestMatchers("/").hasAnyRole("ADMIN", "USER", "VISITOR")
							.requestMatchers("/hello").hasAnyRole("ADMIN", "USER", "VISITOR")
							.requestMatchers("/home").hasAnyRole("ADMIN", "USER")
							.requestMatchers("/user").hasAnyRole("USER", "ADMIN")
							.requestMatchers("/admin").hasRole("ADMIN")
							.anyRequest().authenticated();
				})
				.formLogin(formLogin -> {
					formLogin
							.loginPage("/loginForm")
							.loginProcessingUrl("/process-login")
							.defaultSuccessUrl("/home", true)
							.failureUrl("/loginForm?error=true")
							.permitAll();
				})
				.logout(logout -> {
					logout
							.logoutUrl("/logout")
							.logoutSuccessUrl("/loginForm?logout=true")
							.permitAll();
				});

		return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailService);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}