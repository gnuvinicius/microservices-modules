package br.com.garage.kbn.model.schema;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_projetos")
public class Projeto extends AggregateRoot implements Serializable {

  private String titulo;

  @OneToMany(mappedBy = "projeto", fetch = FetchType.EAGER)
  private List<Task> tasks;

  public Projeto(String titulo, String tenantId, String userId) {
    this.titulo = titulo;
    this.tenantId = UUID.fromString(tenantId);
    this.userId = UUID.fromString(userId);
  }

}
