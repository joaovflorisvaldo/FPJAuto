/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ItemVenda;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Felipe
 */
public class ItemVendaDAO extends GenericDAO<ItemVenda>{
    
    @Override
    protected ItemVenda construirObjeto(ResultSet rs) {
        ItemVenda itemVenda = null;
        try {
        itemVenda = new ItemVenda();
            itemVenda.setId(rs.getInt("ID_ITEM_VENDA"));
            itemVenda.setObservacoes(rs.getString("OBSERVACOES"));
            itemVenda.setData(rs.getString("DATA"));
            itemVenda.setTotal(rs.getDouble("TOTAL"));            
            itemVenda.setCliente(rs.getString("CLIENTE"));
        } catch (SQLException ex) {
            Logger.getLogger(ItemVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemVenda;
    }

   @Override
    public boolean salvar(ItemVenda obj) {
        String sql = "INSERT INTO public.\"Venda\"(\"ID_VENDA\", \"OBSERVACOES\", \"DATA\", \"TOTAL\", \"CLIENTE\")VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getId());
            ps.setString(2, obj.getObservacoes());
            ps.setString(3, obj.getData());
            ps.setDouble(4,obj.getTotal());
            ps.setString(5,obj.getCliente());
            ps.executeUpdate();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void gerarRelatorio(){
              try {
                        //responsavel em carregar o relatorio
                        String relatorioPath =  "Relatorios/relItemVendas.jasper";
                        JasperPrint printer = JasperFillManager.fillReport(relatorioPath, null, conn);
                        
                        //exibicao
                        JasperViewer view = new JasperViewer(printer, false);
                        
                        view.setVisible(true);
                        
              } catch (JRException ex) {
                        Logger.getLogger(ItemVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
    }

    @Override
    public boolean atualizar(ItemVenda obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
