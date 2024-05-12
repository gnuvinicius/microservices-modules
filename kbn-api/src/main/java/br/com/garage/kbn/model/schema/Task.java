package br.com.garage.kbn.model.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_tasks")
public class Task extends AggregateRoot {

  private String titulo;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
  private Projeto projeto;

  public Task(String titulo, String tenantId, String userId, Projeto projeto) {
    this.titulo = titulo;
    this.tenantId = UUID.fromString(tenantId);
    this.userId = UUID.fromString(userId);
    this.projeto = projeto;
  }

}
