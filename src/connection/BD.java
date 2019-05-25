/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;
import com.google.gson.*;
import static connection.ConexaoMySQL.getConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import router.Router;
import util.Cartao;
import util.Data;

/**
 *
 * @author Lincoln
 */
public class BD {
   public static boolean autentica(String login, String pass){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "SELECT senha FROM Usuario WHERE email = '" + login + "'";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                rs.next();
                
                if (pass.equals(rs.getString("senha"))){
                    con.close();
                    return true;
                }else {
                    con.close();
                    return false;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        public static int getId(String login){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "SELECT id FROM Usuario WHERE email = '" + login + "'";
            try {
                
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                rs.next();
                return rs.getInt("id");
                
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return -1;
        }
        public static Cartao[] getCartoes(int id){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            Cartao[] result = new Cartao[100];
            String query = "SELECT * FROM Cartao WHERE id_usuario = '" + id + "'";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                int i=0;
                while(rs.next()){
                    result[i++] = new Cartao(rs.getInt("id"),rs.getInt("id_usuario"),rs.getString("numero"),rs.getDouble("limite"),rs.getDouble("total"),rs.getDate("dt_validade"),rs.getInt("dia_vencimento"));
                }
                result[i] = Cartao.invalido();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
        public static void insereCartao(String numero, double limite, int mes, int ano, int fatura){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "INSERT INTO Cartao (id_usuario, numero, limite, total, dt_validade, dia_vencimento) values (?,?,?,?,?,?)";
            try {
                PreparedStatement SQL = con.prepareStatement(query);
                SQL.setInt(1, Router.getId());
                SQL.setString(2, numero);
                SQL.setDouble(3, limite);
                SQL.setInt(4, 0);
                SQL.setDate(5, Data.dt(ano,mes,Data.getLastDay(mes, ano)));
                SQL.setInt(6, fatura);
                SQL.execute();
                con.close();
                //System.out.println("Cadastrou");
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        public static void insereUsuario(String nome, String email, String senha){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "INSERT INTO Usuario (nome, email, senha) values (?,?,?)";
            try {
                PreparedStatement SQL = con.prepareStatement(query);
                SQL.setString(1, nome);
                SQL.setString(2, email);
                SQL.setString(3, senha);
                SQL.execute();
                con.close();
                //System.out.println("Cadastrou");
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        public static String getDespesasPorCategoria(){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            Map<String,Integer> result = new HashMap<String,Integer>();
            String resultJson;
            String query = "SELECT categoria,sum(valor) as saldo FROM Transacao WHERE month(data) = (?) AND valor < 0 GROUP BY categoria";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                Date date = new java.util.Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                sql.setInt(1, month);
                ResultSet rs = sql.executeQuery();
                while(rs.next()){
                	result.put(rs.getString("categoria"),rs.getInt("saldo"));
                }
                Gson gson = new Gson();
                resultJson = gson.toJson(result);
                return resultJson;
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return "";
        }
        
        public static String getReceitaPorCategoria(){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            Map<String,Integer> result = new HashMap<String,Integer>();
            String resultJson;
            String query = "SELECT categoria,sum(valor) as saldo FROM Transacao WHERE month(data) = (?) AND valor > 0 GROUP BY categoria";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                Date date = new java.util.Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                sql.setInt(1, month);
                ResultSet rs = sql.executeQuery();
                while(rs.next()){
                	result.put(rs.getString("categoria"),rs.getInt("saldo"));
                }
                Gson gson = new Gson();
                resultJson = gson.toJson(result);
                return resultJson;
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return "";
        }
        public static double getSaldoMensal(){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            String query = "SELECT sum(valor) as saldo FROM Transacao WHERE month(data) = (?)";
            double result = 0;
            try {
                PreparedStatement sql = con.prepareStatement(query);
                Date date = new java.util.Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                sql.setInt(1, month);
                ResultSet rs = sql.executeQuery();
                while(rs.next()){
                	result = rs.getDouble("saldo");
                }
                return result ;
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
}
