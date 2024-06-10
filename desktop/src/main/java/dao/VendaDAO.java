/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ItemVenda;
import modelo.Venda;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Felipe
 */
public class VendaDAO extends GenericDAO<Venda>{
    
    @Override
    protected Venda construirObjeto(ResultSet rs) {
        Venda venda = null;
        try {
            if (rs.next()) {
                venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setObservacoes(rs.getString("observacoes"));
                venda.setTotal(rs.getDouble("total"));
                venda.setCliente(rs.getInt("cliente_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }

@Override
public boolean salvar(Venda obj) {
    String vendaSql = "INSERT INTO public.\"venda\"(\"data\", \"observacoes\", \"total\", \"cliente_id\") VALUES (?, ?, ?, ?)";
    String itemSql = "INSERT INTO public.\"item_venda\"(\"quantidade\", \"valor_total\", \"valor_unitario\", \"produto_id\", \"venda_id\") VALUES (?, ?, ?, ?, ?)";

    PreparedStatement vendaPs = null;
    PreparedStatement itemPs = null;

    try {
        conn.setAutoCommit(false); // Desliga o autocommit para permitir uma transação

        // Insere a venda
        vendaPs = conn.prepareStatement(vendaSql, Statement.RETURN_GENERATED_KEYS);
        vendaPs.setDate(1, obj.datenow());
        vendaPs.setString(2, obj.getObservacoes());
        vendaPs.setDouble(3, obj.getTotal());
        vendaPs.setInt(4, obj.getCliente());
        vendaPs.executeUpdate();

        ResultSet vendaKeys = vendaPs.getGeneratedKeys();
        int vendaId = -1;
        if (vendaKeys.next()) {
            vendaId = vendaKeys.getInt(1); // Obtém o ID da venda inserida
        }
        // Insere os itens de venda
        itemPs = conn.prepareStatement(itemSql);
        for (ItemVenda item : obj.getItens()) {
            itemPs.setInt(1, item.getQuantidade());
            itemPs.setDouble(2, item.getValorTotal());
            itemPs.setDouble(3, item.getValorUnitario());
            itemPs.setInt(4, item.getProduto());
            itemPs.setInt(5, vendaId); // Usa o ID da venda inserida
            itemPs.addBatch();
        }
        itemPs.executeBatch();

        conn.commit(); // Confirma a transação

        return true;
    } catch (SQLException ex) {
        try {
            conn.rollback(); // Em caso de falha, faz rollback da transação
        } catch (SQLException e) {
            // Lidar com o erro de rollback, se necessário
        }
        Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (vendaPs != null) vendaPs.close();
            if (itemPs != null) itemPs.close();
            conn.setAutoCommit(true); // Restaura o autocommit para o estado padrão
        } catch (SQLException ex) {
            // Lidar com erros de fechamento de recursos, se necessário
        }
    }
    return false;
}
    
    public void gerarRelatorio(){
              try {
                        //responsavel em carregar o relatorio
                        String relatorioPath =  "src/relatorios/relatorioDetalhado.jasper";
                        JasperPrint printer = JasperFillManager.fillReport(relatorioPath, null, conn);
                        
                        //exibicao
                        JasperViewer view = new JasperViewer(printer, false);
                        
                        view.setVisible(true);

              } catch (JRException ex) {
                        Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
    }
    
    public void gerarRelatorioGeral(){
              try {
                        //responsavel em carregar o relatorio
                        String relatorioPath =  "src/relatorios/relatorioGeral.jasper";
                        JasperPrint printer = JasperFillManager.fillReport(relatorioPath, null, conn);
                        
                        //exibicao
                        JasperViewer view = new JasperViewer(printer, false);
                        
                        view.setVisible(true);

              } catch (JRException ex) {
                        Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
    }

    @Override
    public boolean atualizar(Venda obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
