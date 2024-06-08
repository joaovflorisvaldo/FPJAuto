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
        venda = new Venda();
            venda.setId(rs.getInt("ID_VENDA"));
            venda.setObservacoes(rs.getString("OBSERVACOES"));
            venda.setData(rs.getString("DATA"));
            venda.setTotal(rs.getDouble("TOTAL"));            
            venda.setCliente(rs.getString("CLIENTE"));
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }

   @Override
    public boolean salvar(Venda obj) {
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
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void gerarRelatorio(){
              try {
                        //responsavel em carregar o relatorio
                        String relatorioPath =  "Relatorios/relVendas.jasper";
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
