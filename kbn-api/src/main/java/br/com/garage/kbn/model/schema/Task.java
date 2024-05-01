package br.com.garage.kbn.model.schema;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "tb_tasks")
public class Task extends AggregateRoot {

  private String titulo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
  private Projeto projeto;

  private Task() {}

  public Task(String titulo, String tenantId, String userId, Projeto projeto) {
    this.titulo = titulo;
    this.tenantId = UUID.fromString(tenantId);
    this.userId = UUID.fromString(userId);
    this.projeto = projeto;
  }

}
