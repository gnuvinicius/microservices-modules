package br.com.garage.kbn.repository;

import br.com.garage.kbn.model.schema.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProjetoRepository extends JpaRepository<Projeto, UUID> {

  @Query("SELECT p FROM Projeto p WHERE p.tenantId = :tenantId")
  List<Projeto> findByTenantId(UUID tenantId);
  
}
