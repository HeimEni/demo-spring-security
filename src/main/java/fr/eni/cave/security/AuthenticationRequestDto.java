package fr.eni.cave.security;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "pseudo")
public class AuthenticationRequestDto {
	private String pseudo;
	private String password;
}
