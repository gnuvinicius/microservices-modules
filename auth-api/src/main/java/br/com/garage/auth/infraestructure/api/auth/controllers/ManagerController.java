package br.com.garage.auth.infraestructure.api.auth.controllers;

import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.service.IManagerService;
import br.com.garage.auth.infraestructure.api.auth.dtos.TenantRequestDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UsuarioRequestDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UsuarioResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController()
@RequestMapping("/auth/api/v1/manager")
public class ManagerController {

	@Autowired
	private final IManagerService service;

	public ManagerController(IManagerService service) {
		this.service = service;
	}

	@GetMapping("/usuarios")
	public ResponseEntity<?> findAllUsersByCompany() throws Exception {
		return ResponseEntity.ok(service.listaTodosUsuarios());
	}

	@PostMapping("/usuarios")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UsuarioResponseDto> cadastra(@RequestBody @Valid UsuarioRequestDto request)
			throws Exception {
		return ResponseEntity.ok(service.cadastraUsuario(request));
	}

	@DeleteMapping("/usuario")
	public ResponseEntity<?> arquiva(@RequestParam(name = "id") UUID id) throws Exception {
		service.arquiva(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/empresas")
	public ResponseEntity<?> listaTodas() throws Exception {
		return ResponseEntity.ok(service.listaTodas());
	}

	@PostMapping("/empresas")
	public ResponseEntity<?> cadastra(@RequestBody TenantRequestDto request) throws Exception {
		Tenant tenant = service.cadastraTenant(request);

		URI uri = new URI(tenant.getId().toString());
		return ResponseEntity.created(uri).build();
	}
}
