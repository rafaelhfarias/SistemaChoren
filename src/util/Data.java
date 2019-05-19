/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Calendar;

/**
 *
 * @author Lincoln
 */
public class Data {
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
    public static void main(String args[]){
        System.out.println(getLastDay(2,2019));
    }
}
