package br.com.garage.auth.infraestructure.database.auth;

import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.domains.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface IUserRepository extends JpaRepository<Usuario, UUID> {

	@Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.status = :status")
	Optional<Usuario> buscaPorEmail(String email, Status status);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.status = :status AND u.tenant = :tenant")
	Optional<Usuario> buscaPorEmailAndStatus(String email, Status status, Tenant tenant);

	@Query("SELECT u FROM Usuario u WHERE u.status = :status AND u.tenant = :tenant")
	List<Usuario> buscaPorTenant(Status status, Tenant tenant);

	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(String email);
}