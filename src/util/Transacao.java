/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Lincoln
 */
public class Transacao {
    private int id;
    private int id_usuario;
    private String nome;
    private double valor;
    private Date data;
    private String categoria;
    private int cartao;
    private int parcela_atual;
    private int parcela_total;

    public Transacao(int id, int id_usuario, String nome, double valor, Date data, String categoria, int cartao, int parcela_atual, int parcela_total) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.cartao = cartao;
        this.parcela_atual = parcela_atual;
        this.parcela_total = parcela_total;
    }

    public static Transacao invalido(){
        return new Transacao(-1,-1,"",0,new Date(Calendar.getInstance().getTime().getTime()),"@",-2,-1,-1);
    }
    
    public int getId() {
        return id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCartao() {
        return cartao;
    }

    public int getParcela_atual() {
        return parcela_atual;
    }

    public int getParcela_total() {
        return parcela_total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCartao(int cartao) {
        this.cartao = cartao;
    }

    public void setParcela_atual(int parcela_atual) {
        this.parcela_atual = parcela_atual;
    }

    public void setParcela_total(int parcela_total) {
        this.parcela_total = parcela_total;
    }
    
    
}
