package fr.eni.cave.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.*;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	/**
	 * Authentification et détail de l'utilisateur depuis la base de données
	 */
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Bean
	UserDetailsService userDetailsService() {
		return username -> userInfoRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
