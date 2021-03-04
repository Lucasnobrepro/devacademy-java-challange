package br.com.casamagalhaes.workshop.desafio.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedido;

    private String nomeCliente;

    @NotEmpty(message = "Endereço é obrigatória")
    @Size(min = 4, max = 100, message = "Endereço deve ter no mínimo 4 e no máximo 100 caracteres")
    private String endereco;

    private Long telefone;

    private Double valorTotalProdutos;

    private Double taxa;

    private Double valorTotal;

    private String status;

    @Valid
    @NotEmpty(message = "Não pode esta vazio")
    @Size(min = 1, message = "Deve possuir pelo menos 1 item")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itens = new ArrayList<Item>();
}

