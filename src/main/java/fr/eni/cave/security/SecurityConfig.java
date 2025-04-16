package fr.eni.cave.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Requête SQL pour récupérer l'utilisateur
        manager.setUsersByUsernameQuery(
                "SELECT pseudo, mot_de_passe, 1 as enabled FROM cav_user WHERE pseudo=?");

        // Requête SQL pour récupérer les rôles
        manager.setAuthoritiesByUsernameQuery(
                "SELECT pseudo, authority FROM cav_user WHERE pseudo=?");

        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // Routes accessibles à tous (même non authentifiés)
                        .requestMatchers(HttpMethod.GET, "/caveavin/bouteilles/**").permitAll()

                        // Routes accessibles aux clients
                        .requestMatchers(HttpMethod.POST, "/caveavin/paniers").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/caveavin/paniers").hasRole("CLIENT")

                        // Routes accessibles au propriétaire
                        .requestMatchers(HttpMethod.POST, "/caveavin/bouteilles/**").hasRole("PROPRIO")
                        .requestMatchers(HttpMethod.PUT, "/caveavin/bouteilles/**").hasRole("PROPRIO")
                        .requestMatchers(HttpMethod.POST, "/caveavin/regions/**").hasRole("PROPRIO")
                        .requestMatchers(HttpMethod.POST, "/caveavin/couleurs/**").hasRole("PROPRIO")
                        .requestMatchers(HttpMethod.GET, "/caveavin/regions/**").hasAnyRole("PROPRIO", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/caveavin/couleurs/**").hasAnyRole("PROPRIO", "CLIENT")

                        // Routes pour voir les paniers - accessibles aux deux rôles
                        .requestMatchers(HttpMethod.GET, "/caveavin/paniers/**").hasAnyRole("PROPRIO", "CLIENT")

                        // Toutes les autres routes sont interdites
                        .anyRequest().authenticated()
                )
                .httpBasic()  // Pour pouvoir utiliser Postman
                .and()
                .csrf().disable();  // Désactiver CSRF pour les API REST

        return http.build();
    }
}