package fr.eni.cave.security;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class EniSecurityConfig {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private JwtAuthenticationFilter jwFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	/**
	 * Restriction des URLs selon la connexion utilisateur et leurs rôles
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth
					// Permettre l'accès à l'URL racine à tout le monde
					.requestMatchers("/eniecole").permitAll()
					.requestMatchers("/auth/**").permitAll()
					// Permettre aux rôles EMPLOYE et ADMIN de manipuler les URLs en GET
					.requestMatchers(HttpMethod.GET, "/eniecole/employes/**").hasAnyRole("EMPLOYE", "ADMIN")
					// Restreindre la manipulation des méthodes POST, PUT, PATCH et DELETE au rôle
					// ADMIN
					.requestMatchers(HttpMethod.POST, "/eniecole/employes").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/eniecole/employes").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PATCH, "/eniecole/employes").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/eniecole/employes").hasRole("ADMIN")
					// Toutes autres url et méthodes HTTP ne sont pas permises
					.anyRequest().denyAll();

		});

		// configuration du jwtFilter

		// pas bien mais c'est une demo
		http.csrf(csrf -> csrf.disable());

		http.addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(authenticationProvider);

		// plus de possibilité de session.
		http.sessionManagement(session -> {
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});

		return http.build();
	}

}
