/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Date;

/**
 *
 * @author Lincoln
 */
public class Cartao {
    private String numero;
    private int id;
    private int idUser;
    private double limite;
    private java.sql.Date dtValidade;
    private double total;
    private int diaVenc;

    public Cartao(int id, int idUser, String numero, double limite, double total, Date dtValidade, int diaVenc){
        setId(id);
        setIdUser(idUser);
        setNumero(numero);
        setLimite(limite);
        setTotal(total);
        setDtValidade(dtValidade);
        setDiaVenc(diaVenc);
    }
    public static Cartao invalido(){
        return new Cartao(-1,-1,"@",-1,-1,new Date(0),-1);
    }
            
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public Date getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(int ano, int mes) {
        this.dtValidade = Data.dt(ano, mes, Data.getLastDay(mes, ano));
    }
    
    public void setDtValidade(Date dtValidade) {
        this.dtValidade = dtValidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getDiaVenc() {
        return diaVenc;
    }

    public void setDiaVenc(int diaVenc) {
        this.diaVenc = diaVenc;
    }
    
}
