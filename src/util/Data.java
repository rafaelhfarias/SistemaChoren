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
public class Data {
    public static java.sql.Date inicioFat(int dia){
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_MONTH) <= dia){
            cal.set(Calendar.DAY_OF_MONTH, dia);
            cal.add(Calendar.MONTH, -1);
        }
        else{
            cal.set(Calendar.DAY_OF_MONTH, dia);
        }
        return new java.sql.Date(cal.getTime().getTime());
    }
    public static java.sql.Date fimFat(int dia){
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_MONTH) <= dia){
            cal.set(Calendar.DAY_OF_MONTH, dia);
        }
        else{
            cal.set(Calendar.DAY_OF_MONTH, dia);
            cal.add(Calendar.MONTH, 1);
        }
        return new java.sql.Date(cal.getTime().getTime());
    }
    public static java.sql.Date dt(int ano, int mes, int dia){
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes-1, dia);
        return new java.sql.Date(cal.getTime().getTime());
    }
    public static int getLastDay(int mes, int ano){
        if (mes < 8){
            if (mes == 2 && (ano%4) == 0 && (ano%100) != 0)return 29;
            else if (mes == 2)return 28;
            else return (30+(mes%2));
        }
        else{
            return (31-(mes%2));
        }
    }
    public static boolean beforeM(java.sql.Date val, Date data, int parcela){
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.MONTH, parcela);
        return val.before(new java.sql.Date(c.getTime().getTime()));
    }
    public static java.sql.Date addM(java.sql.Date val, int parcela){
        Calendar c = Calendar.getInstance();
        c.setTime(val);
        c.add(Calendar.MONTH, parcela);
        return new java.sql.Date(c.getTime().getTime());
    }
    public static void main(String args[]){
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        System.out.println();
    }
}
