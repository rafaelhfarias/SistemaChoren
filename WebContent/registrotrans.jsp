<%-- 
    Document   : registrotrans
    Created on : 16/05/2019, 20:13:47
    Author     : Lincoln
--%>

<%@page import="connection.BD"%>
<%@page import="util.Cartao"%>
<%@page import="router.Router"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="connection.ConexaoMySQL"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Registro de Transação</title>
</head>
<body>
<form action="Router" method="get">	
		<table>
			<tr>
				<td>Nome:</td>
				<td>		 <input type="text" name="name">
</td>
			</tr>
			<tr>
				<td>Valor:</td>
				<td>	 <input type="number" step="0.01" name="price"></td>
			</tr>
			<tr>
				<td>Data:</td>
				<td>	<input type="date" name="date">
  				</td>
			</tr>
			<tr>
				<td>Categoria:</td>
				<td>	 <select name="category">
                            <option value="Receita">Receita</option>
                            <option value="Alimentacao">Alimentação</option>
                            <option value="Casa">Casa</option>
                            <option value="Comunicacao">Comunicação</option>
                            <option value="Despesas Pessoais">Despesas Pessoais</option>
                            <option value="Educacao">Educação</option>
                            <option value="Investimento">Investimento</option>
                            <option value="Lazer">Lazer</option>
                            <option value="Saude">Saúde</option>
                            <option value="Tarifas e Impostos">Tarifas e Impostos</option>
                            <option value="Transporte">Transporte</option>
                            <option value="Outros">Outros</option>
                        </select></td>
			</tr>
                        <tr>
				<td>Cartão:</td>
				<td>
                                    <select name="card">
                                        <option value="-1">Sem cartão</option>
                                        <%Cartao[] cartoes = BD.getCartoes(Router.getId());
                                            int tam = 0;
                                            while (cartoes[tam].getId() != -1){
                                                out.print("<option value='" + String.valueOf(cartoes[tam].getId()) + "'>" + String.valueOf(cartoes[tam].getNumero()) + "</option>");
                                                tam++;
                                            }
                                        %>
                                        
                                    </select></td>
			</tr>
                        <tr>
				<td>Parcelas:</td>
                                <td>	<input type="number" step="1" name="parcela" min="1" max="12">
  				</td>
			</tr>
		</table>
		<br/>

	<button type="submit" name="path" value="registrartrans">Cadastrar</button>
</form>
</body>
</html>
