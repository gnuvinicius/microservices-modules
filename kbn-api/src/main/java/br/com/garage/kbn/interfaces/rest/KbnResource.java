package br.com.garage.kbn.interfaces.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.garage.kbn.dto.ProjetoDTO;
import br.com.garage.kbn.dto.TaskDTO;
import br.com.garage.kbn.model.schema.Projeto;
import br.com.garage.kbn.model.schema.Task;
import br.com.garage.kbn.repository.ProjetoRepository;
import br.com.garage.kbn.repository.TaskRepository;
import br.com.garage.kbn.shared.AbstractResource;
import br.com.garage.kbn.shared.MessageError;

@RestController
@RequestMapping(value = "/kbn/api/v1")
public class KbnResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(KbnResource.class);
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/projetos/{tenantId}")
    public ResponseEntity<?> getAll(@RequestParam String tenantId) {
        try {
            return ResponseEntity.ok(projetoRepository.findByTenantId(UUID.fromString(tenantId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageError(400, e.getMessage()));
        }
    }

    @PostMapping("/projetos/{tenantId}/{userId}")
    public ResponseEntity<?> cadastraProjeto(
            @RequestBody ProjetoDTO dto, @RequestParam String tenantId, @RequestParam String userId)
            throws URISyntaxException {
        logger.info("iniciando cadastro novo projeto");

        try {
            loadJwtRequest();
            var projeto = new Projeto(dto.getTitulo(), tenantId, userId);

            Projeto entity = projetoRepository.save(projeto);
            logger.info("cadastro realizado com sucesso: {}", entity.getTitulo());
            return ResponseEntity.created(new URI(entity.getId().toString())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageError(400, e.getMessage()));
        }
    }

    @PostMapping("/tasks/{tenantId}/{userId}")
    public ResponseEntity<?> cadastraTask(@RequestBody TaskDTO dto, @RequestParam String tenantId, @RequestParam String userId) {
        loadJwtRequest();
        Optional<Projeto> optional = projetoRepository.findById(dto.projetoId);

        try {
            if (optional.isPresent()) {
                optional.ifPresent(projeto -> {
                    taskRepository.save(new Task(dto.titulo, tenantId, userId, projeto));
                    logger.info("task: {} adicionada ao prejeto: {}", dto.titulo, dto.projetoId);
                });
                return ResponseEntity.ok().build();
            } else {
                logger.warn("projeto com id: '{}' não encontrado", dto.projetoId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("erro ao criar task: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
