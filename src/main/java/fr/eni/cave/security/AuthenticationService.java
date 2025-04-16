package fr.eni.cave.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthenticationService {

	private UserInfoRepository userInfoRepository;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;

	public String authenticate(AuthenticationRequestDto request) {
		
		var authenticationToken=new UsernamePasswordAuthenticationToken(request.getPseudo(), request.getPassword());
		authenticationManager.authenticate(authenticationToken);
		
		var user=userInfoRepository.findById(request.getPseudo()).orElseThrow();
		return jwtService.generateToken(user);
	}

}
