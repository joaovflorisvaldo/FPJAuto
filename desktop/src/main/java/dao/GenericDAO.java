package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import conexaoBD.ConexaoPostgres;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public abstract class GenericDAO<Object> {
    
    public Connection conn = null;
    public GenericDAO(){
        try {
            conn = ConexaoPostgres.getConection();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected abstract Object construirObjeto(ResultSet rs);
    
    public abstract boolean salvar(Object obj);
    
    public abstract boolean atualizar(Object obj);
    
    public ArrayList<Object> retornaLista(String sql){
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object> lista = new ArrayList<>();
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        
            while(rs.next()){
                lista.add(construirObjeto(rs));
            }
            ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;  
    }
    
    public Object retornarPeloId(int id, String tabela, String chavePrimaria){
        PreparedStatement ps;
        ResultSet rs;
        Object obj = null;
        
        try {
            ps = conn.prepareStatement("SELECT * FROM public.\""+tabela+"\" WHERE "+"\""+chavePrimaria+"\" = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                obj = construirObjeto(rs);
            }
            
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return obj;
    }
    
    public void delete(int id, String tabela, String chavePrimaria){
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("DELETE FROM"+"public.\""+tabela+"\" WHERE \""+chavePrimaria+"\" = ? ");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
}