package br.com.casamagalhaes.workshop.desafio.model;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Não pode esta vazio")
    private String descricao;

    @NotNull(message = "Não pode esta vazio")
    private Double precoUnitario;

    @NotNull(message = "Não pode esta vazio")
    private Integer quantidade;

}
