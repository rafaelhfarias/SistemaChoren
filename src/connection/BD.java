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
import java.sql.Types;
import java.text.ParseException;
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
                    rs.close();
                    return true;
                }else {
                    rs.close();
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
            int result = -1;
            try {
                
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                rs.next();
                result = rs.getInt("id");
                rs.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
        public static String getNome(int id){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "SELECT nome FROM Usuario WHERE id = '" + id + "'";
            String result = "Desconhecido";
            try {
                
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                rs.next();
                result = rs.getString("nome");
                rs.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
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
                rs.close();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
        public static double getLimiteDisp(int id){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            double result = 0;
            String query = "SELECT limite, total FROM Cartao WHERE id = '" + id + "'";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                int i=0;
                while(rs.next()){
                    result = rs.getDouble("limite");
                    result -= rs.getDouble("total");
                }
                rs.close();
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
                SQL.close();
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
                SQL.close();
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
                rs.close();
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
                rs.close();
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
                rs.close();
                return result ;
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
        public static java.sql.Date getValidade(int id){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            java.sql.Date result = new java.sql.Date(0);
            String query = "SELECT dt_validade FROM Cartao WHERE id = '" + id + "'";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                int i=0;
                while(rs.next()){
                    result = rs.getDate("dt_validade");
                }
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
        
        public static int getDiaFat(int id){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            int result = 1;
            String query = "SELECT dia_vencimento FROM Cartao WHERE id = '" + id + "'";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                while(rs.next()){
                    result = rs.getInt("dia_vencimento");
                    
                }
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
        }
        
        public static void atualizaTotal(int id){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            String query = "SELECT * FROM Cartao WHERE id_usuario = '" + id + "'";
            int[] idCartao = new int[100];
            int[] diaFat = new int[100];
            int i=0;
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                
                while(rs.next()){
                    diaFat[i] = rs.getInt("dia_vencimento");
                    idCartao[i++] = rs.getInt("id");
                }
                rs.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = ConexaoMySQL.getConexaoMySQL();
            for (int j=0;j<i;j++){
                query = "UPDATE Cartao c SET c.total=(CASE WHEN (SELECT COUNT(valor) FROM Transacao t WHERE t.cartao=c.id AND t.data BETWEEN '" + String.valueOf(Data.inicioFat(diaFat[j])) + "' AND '" + String.valueOf(Data.fimFat(diaFat[j])) + "' GROUP BY cartao) > 0 THEN (SELECT SUM(-valor) FROM Transacao t WHERE t.cartao=c.id AND t.data BETWEEN '" + String.valueOf(Data.inicioFat(diaFat[j])) + "' AND '" + String.valueOf(Data.fimFat(diaFat[j])) + "' GROUP BY cartao) ELSE '0' END)";
                try {
                    PreparedStatement sql = con.prepareStatement(query);
                    sql.executeUpdate();
                    sql.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
                }

                
            }
        }
        public static void insereTrans(int id_usuario,String nome, double valor, java.sql.Date data, String categoria, int cartao, int p_atual, int p_total){
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "INSERT INTO Transacao (id_usuario, nome, valor, data, categoria, cartao, parcela_atual, parcela_total) values (?,?,?,?,?,?,?,?)";
            try {
                valor = -valor;
                PreparedStatement SQL = con.prepareStatement(query);
                SQL.setInt(1, id_usuario);
                SQL.setString(2, nome);
                SQL.setDouble(3, valor);
                SQL.setDate(4, data);
                SQL.setString(5, categoria);
                SQL.setInt(6, cartao);
                SQL.setInt(7, p_atual);
                SQL.setInt(8, p_total);
                SQL.execute();
                SQL.close();
                //System.out.println("Cadastrou");
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        }
        public static void insereTrans(int id_usuario,String nome, double valor, java.sql.Date data, String categoria, int p_atual, int p_total) throws ParseException{
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "INSERT INTO Transacao (id_usuario, nome, valor, data, categoria, cartao, parcela_atual, parcela_total) values (?,?,?,?,?,?,?,?)";
            try {
                if (!categoria.equals("Receita"))valor = -valor;
                PreparedStatement SQL = con.prepareStatement(query);
                SQL.setInt(1, id_usuario);
                SQL.setString(2, nome);
                SQL.setDouble(3, valor);
                SQL.setDate(4, data);
                SQL.setString(5, categoria);
                SQL.setNull(6, Types.INTEGER);
                SQL.setInt(7, p_atual);
                SQL.setInt(8, p_total);
                SQL.execute();
                SQL.close();
                //System.out.println("Cadastrou");
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        public static double getSaldo(int id){
            double result = 0;
            Connection con = ConexaoMySQL.getConexaoMySQL();
            //System.out.println(status);
            String query = "SELECT valor FROM Transacao WHERE id_usuario = '" + id + "' AND data < CURRENT_DATE()";
            try {
                PreparedStatement sql = con.prepareStatement(query);
                ResultSet rs = sql.executeQuery();
                while(rs.next()){
                    result += rs.getDouble("valor");
                }
                rs.close();
                
                //System.out.println("Cadastrou");
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        }
}
