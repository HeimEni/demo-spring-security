package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "CAV_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

	@Id
	@Column(name = "LOGIN", nullable = false, length = 255)
	@EqualsAndHashCode.Include
	private String pseudo;
	
	@ToString.Exclude
	@Column(name = "PASSWORD", nullable = false, length = 68)
	private String password;
	
	@Column(name = "LAST_NAME", nullable = false, length = 90)
	private String nom;
	
	@Column(name = "FIRST_NAME", nullable = false, length = 150)
	private String prenom;

}