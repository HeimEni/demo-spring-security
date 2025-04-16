package fr.eni.cave.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class UserInfo implements UserDetails {
	@Id
	@Column(length = 250)
	private String pseudo;

	@Column(length = 68, nullable = false)
	private String password;

	@Column(length = 15, nullable = false)
	private String authority;

	// Correspond aux rôles de l'utilisateur
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(authority));
	}

	// Correspond à l'élément unique d'authentification
	@Override
	public String getUsername() {
		return pseudo;
	}

	// Etat du compte utilisateur – compte non expiré ?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// Etat du compte utilisateur – non verrouillé ?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// Indique si les informations d’identification sont non expirées ?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// Etat du compte utilisateur – actif ?
	@Override
	public boolean isEnabled() {
		return true;
	}
}
