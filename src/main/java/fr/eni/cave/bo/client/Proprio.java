package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "CAV_OWNER")
@ToString(callSuper = true)
public class Proprio extends Utilisateur{
	
	@Column(name = "CLIENT_NUMBER")
	private String siret;
}
