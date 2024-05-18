package br.com.garage.auth.infraestructure.database;

import br.com.garage.auth.domains.models.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ITenantRepository extends JpaRepository<Tenant, UUID> {

	@Query("SELECT e.email FROM Tenant e WHERE e.id = :id")
	String getEmailToSendEmail(UUID id);

	@Query("SELECT e FROM Tenant e WHERE e.cnpj = :cnpj")
    Optional<Tenant> buscarPorCnpj(String cnpj);

}
