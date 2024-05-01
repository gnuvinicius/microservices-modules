package br.com.garage.auth.infraestructure.database.auth;

import br.com.garage.auth.domains.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {

	@Query("SELECT r FROM Role r WHERE r.roleName IN :roleNameList")
	List<Role> findByRoleNameList(List<String> roleNameList);

	Optional<Role> findByRoleName(String roleName);

}
