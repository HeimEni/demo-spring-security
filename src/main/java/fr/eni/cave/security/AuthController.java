package fr.eni.cave.security;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationService authenticationService;

	public AuthController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	
	@PostMapping
	public ResponseEntity<String> getToken(@RequestBody AuthenticationRequestDto request) {
		return ResponseEntity.ok( authenticationService.authenticate(request));
	}
	
}
