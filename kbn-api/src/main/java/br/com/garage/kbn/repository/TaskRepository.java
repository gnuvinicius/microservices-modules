package br.com.garage.kbn.repository;

import br.com.garage.kbn.model.schema.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
