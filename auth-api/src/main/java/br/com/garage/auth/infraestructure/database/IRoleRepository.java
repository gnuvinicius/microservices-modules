package br.com.garage.auth.infraestructure.database;

import br.com.garage.auth.domains.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {

	Optional<Role> findByRoleName(String roleName);

}
