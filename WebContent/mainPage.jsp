<%@ page import="connection.ConexaoMySQL"%>
<%@ page import="connection.BD"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="util.*"%>
<%@ page import="router.Router"%>
<%@ page import="com.google.gson.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>Suas financas</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

</head>

<body>
	<nav
		class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Bem vindo
			às suas finanças, <%=BD.getNome(Router.getId())%></a>
		<ul class="navbar-nav px-3">
			<li class="nav-item text-nowrap"><a class="nav-link" href="#">Sign
					out</a></li>
		</ul>
	</nav>
	<div class="sidebar-sticky">
		<ul class="nav flex-column">
			<li class="nav-item"><a class="nav-link active"
				href="mainPage.jsp"> <span data-feather="home"></span> Inicio
			</a></li>
			<li class="nav-item"><a class="nav-link" href="graficos.jsp">
					<span data-feather="shopping-cart"></span> Graficos
			</a></li>
		</ul>

	</div>

	<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"> <%
 	double saldo = BD.getSaldoMensal(Router.getId());
 %>
	<h3>
		Saldo mensal:
		<%
		if (saldo >= 0) {
			out.print("R$ " + String.valueOf(saldo));
		} else
			out.print("<font color='FF0000'>-R$ " + String.valueOf(-saldo) + "</font>");
	%>
	</h3>
	<br><br>
	<h4>
		Cartões de crédito:
	</h4>
	<div>
		<table class="table table-striped table-sm">
			<%
				Cartao[] cartoes = BD.getCartoes(Router.getId());
				out.print("<th>Numero</th>");
				out.print("<th>Limite</th>");
				out.print("<th>Valor Gasto</th>");
				String numero;
				int tamC = 0;
				while (cartoes[tamC].getId() != -1) {
					out.print("<tr>");
					numero = cartoes[tamC].getNumero();
					out.print("<td>" + numero.substring(0,4) + " XXXX XXXX " + numero.substring(12,16)+ "</td>");
					out.print("<td>" + String.valueOf(cartoes[tamC].getLimite()) + "</td>");
					out.print("<td>" + String.valueOf(cartoes[tamC].getTotal()) + "</td>");
					out.print("</tr>");
					tamC++;
				}
			%>
		</table>
	</div>
	<hr/>
	<h4>
		Extrato mensal:
	</h4>
	<div>
		<table class="table table-striped table-sm">
			<%
				Transacao[] transacoes = BD.getTransacoesMensal(Router.getId());
				out.print("<th>Nome</th>");
				out.print("<th>Valor</th>");
				out.print("<th>Data</th>");
				out.print("<th>Categoria</th>");
				out.print("<th>Parcela</th>");
				int tam = 0;
				while (transacoes[tam].getId() != -1) {
					out.print("<tr>");
					out.print("<td>" + String.valueOf(transacoes[tam].getNome()) + "</td>");
					out.print("<td>" + String.valueOf(transacoes[tam].getValor()) + "</td>");
					out.print("<td>" + String.valueOf(transacoes[tam].getData()) + "</td>");
					out.print("<td>" + String.valueOf(transacoes[tam].getCategoria()) + "</td>");
					out.print("<td>" + String.valueOf(transacoes[tam].getParcela_atual()) + "/"
							+ String.valueOf(transacoes[tam].getParcela_total()) + "</td>");
					out.print("</tr>");
					tam++;
				}
			%>
		</table>
	</div>
	</main>

	<form action="Router" method="get">
		<%
			BD.atualizaTotal(Router.getId());
		%>
		<div align="center">
			<button class="btn btn-dark" type="submit" name="path"
				value="Cadastrar Cartao">Cadastrar Cartao</button>
			<button class="btn btn-dark" type="submit" name="path"
				value="Registrar Transacao">Registrar Transacao</button>
		</div>
	</form>
	<%
		String despesasMapJson = BD.getDespesasPorCategoria(Router.getId());
		String receitaMapJson = BD.getReceitaPorCategoria(Router.getId());
	%>

	<script type="text/javascript" src="js/bootstrap.js"></script>





</body>

</html>