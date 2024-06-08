/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            venda.setData(rs.getDate("DATA"));
            venda.setTotal(rs.getDouble("TOTAL"));            
            venda.setCliente(rs.getInt("CLIENTE"));
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }

   @Override
    public boolean salvar(Venda obj) {
        String sql = "INSERT INTO public.\"Venda\"(\"OBSERVACOES\", \"DATA\", \"TOTAL\", \"CLIENTE\")VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            LocalDate dataAtual = LocalDate.now();
            String dataFormatada = dataAtual.format(formatter);
        } catch (Exception e){
        }

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getObservacoes());
            ps.setDate(2,obj.getData());
            ps.setDouble(3,obj.getTotal());
            ps.setInt(4,obj.getCliente());
            ps.executeUpdate();
            ps.close();
            
            return true;
        } catch (SQLException ex) {
            System.out.println("deu erro"+ ex);
//            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
