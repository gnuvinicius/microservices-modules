package br.com.garage.kbn.model.schema;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_tasks")
public class Task extends AggregateRoot {
  private String titulo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
  private Projeto projeto;

  public Task(String titulo, String tenantId, String userId) {
    this.titulo = titulo;
    this.tenantId = UUID.fromString(tenantId);
    this.userId = UUID.fromString(userId);
  }
}
