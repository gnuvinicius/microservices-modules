package br.com.garage.kbn.interfaces.rest;

import br.com.garage.kbn.dto.ProjetoDTO;
import br.com.garage.kbn.dto.TaskDTO;
import br.com.garage.kbn.model.schema.Projeto;
import br.com.garage.kbn.model.schema.Task;
import br.com.garage.kbn.repository.ProjetoRepository;
import br.com.garage.kbn.repository.TaskRepository;
import br.com.garage.kbn.shared.AbstractResource;
import br.com.garage.kbn.shared.MessageError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/kbn/api/v1")
public class KbnResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(KbnResource.class);
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/projetos")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAll() {
        loadJwtRequest();
        try {
            return ResponseEntity.ok(projetoRepository.findByTenantId(UUID.fromString(request.tenantId())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageError(400, e.getMessage()));
        }
    }

    @PostMapping("/projetos")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cadastraProjeto(@RequestBody ProjetoDTO dto) throws URISyntaxException {
        logger.info("iniciando cadastro novo projeto");

        try {
            loadJwtRequest();
            var projeto = new Projeto(dto.getTitulo(), request.tenantId(), request.userId());

            Projeto entity = projetoRepository.save(projeto);
            logger.info("cadastro realizado com sucesso: {}", entity.getTitulo());
            return ResponseEntity.created(new URI(entity.getId().toString())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageError(400, e.getMessage()));
        }
    }

    @PostMapping("/tasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cadastraTask(@RequestBody TaskDTO dto) {
        loadJwtRequest();
        Optional<Projeto> optional = projetoRepository.findById(dto.projetoId);

        optional.ifPresent(projeto -> {
            taskRepository.save(new Task(dto.titulo, request.tenantId(), request.userId(), projeto));
        });

        return ResponseEntity.ok().build();
    }

}
