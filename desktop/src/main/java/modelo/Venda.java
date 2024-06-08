/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Date;

/**
 *
 * @author Felipe
 */
public class Venda {
    
    private Integer id;
    private String observacoes;
    private Date data ;
    private Double total;
    private Integer cliente;
    
    public Venda(){}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getObservacoes() {return observacoes;}

    public void setObservacoes(String observacoes) {this.observacoes = observacoes;}

    public Date getData() {return data;}

    public void setData(Date data) {this.data = data;}

    public Double getTotal() {return total;}

    public void setTotal(Double total) {this.total = total;}

    public Integer getCliente() {return cliente;}

    public void setCliente(Integer cliente) {this.cliente = cliente;}
    
    
}
