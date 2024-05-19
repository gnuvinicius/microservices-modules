package br.com.garage.auth.infraestructure.rest;

import br.com.garage.auth.config.ResponseErrorBody;
import br.com.garage.auth.domains.service.AuthService;
import br.com.garage.auth.infraestructure.rest.dtos.RequestRefreshPasswordDto;
import br.com.garage.auth.infraestructure.rest.dtos.TokenDto;
import br.com.garage.auth.infraestructure.rest.dtos.UserLoginRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/auth/api/v1")
public class AuthController {

	private final AuthService service;

	public AuthController(AuthService service) {
		this.service = service;
	}

	@ApiResponse(responseCode = "200", description = "Found the book",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = TokenDto.class)) })
	@PostMapping(value = "login")
	public ResponseEntity<?> auth(@RequestBody UserLoginRequestDto dto) throws AuthenticationException {
		return ResponseEntity.ok(service.auth(dto));
	}

	@GetMapping(value = "/reset-password")
	public ResponseEntity<?> requestUpdatePassword(@RequestParam(name = "login") String login) throws Exception {
		service.solicitaAtualizarPassword(login);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/reset-password")
	public ResponseEntity<?> updatePasswordByRefreshToken(@RequestBody RequestRefreshPasswordDto dto) throws Exception {
		service.updatePasswordByRefreshToken(dto);
		return ResponseEntity.ok().build();
	}
}
