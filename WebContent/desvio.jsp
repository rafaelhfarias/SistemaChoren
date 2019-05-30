<%-- 
    Document   : desvio
    Created on : 30/05/2019, 09:25:34
    Author     : Lincoln
--%>
<%@ page import = "router.Router"%>
<%@ page import = "util.Transacao"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import="connection.BD"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Suas Finanças</title>
</head>
<body>
    <form action="Router" method="get">
        <table>
            <tr>
                
                <td>Bem vindo <%=BD.getNome(Router.getId())%></td>
            </tr>
        </table>
        <input type="submit" name="path" value="Cadastrar Cartão">
        <input type="submit" name="path" value="Registrar Transacao">
    </form>
    <table>
        <tr>
            <%Transacao[] transacoes = BD.getTransacoesMensal(Router.getId());
                                            out.print("<th>Nome</th>");
                                            out.print("<th>Valor</th>");
                                            out.print("<th>Data</th>");
                                            out.print("<th>Categoria</th>");
                                            out.print("<th>Parcela</th>");
                                            int tam = 0;
                                            while (transacoes[tam].getId() != -1){
                                                out.print("<td>" + String.valueOf(transacoes[tam].getNome()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getValor()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getData()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getCategoria()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getParcela_atual()) + "/" + String.valueOf(transacoes[tam].getParcela_total()) + "</td>");
                                                tam++;
                                            }
                                        %>
            
        </tr>
    </table>  

	
</body>
</html>