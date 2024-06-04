package br.FPJAuto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String observacoes;

    private String data ;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id" )
    private Cliente cliente;



}
