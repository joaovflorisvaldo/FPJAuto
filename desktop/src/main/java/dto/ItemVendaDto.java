package dto;

import java.util.List;
import modelo.ItemVenda;

public class ItemVendaDto {

    private int quantidade;
    private double valorUnitario;
    private double valorTotal;
    private VendaDto venda;
    private ProdutoDto produto;
    private List<ItemVenda> itens;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public VendaDto getVenda() {
        return venda;
    }

    public void setVenda(VendaDto venda) {
        this.venda = venda;
    }

    public ProdutoDto getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDto produto) {
        this.produto = produto;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
}
