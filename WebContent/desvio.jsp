<%-- 
    Document   : desvio
    Created on : 30/05/2019, 09:25:34
    Author     : Lincoln
--%>
<%@ page import = "router.Router"%>
<%@ page import = "util.Transacao"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import="connection.BD"%>
<%@ page import="com.google.gson.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<title>Suas Finanças</title>
</head>
<body>
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Bem vindo às suas finanças, <%=BD.getNome(Router.getId())%></a>
    </nav>
     <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="#">
                    <span data-feather="home"></span>
                    Inicio <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="file"></span>
                    Cartoes
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="shopping-cart"></span>
                    Graficos
                </a>
            </li>
        </ul>

    </div>
    
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
        <%double saldo = BD.getSaldoMensal(Router.getId());%>
        <h3>Saldo mensal:
            <%if (saldo >= 0){
                out.print("R$ " + String.valueOf(saldo));
            }
            else out.print("<font color='FF0000'>-R$ " + String.valueOf(-saldo) + "</font>");%>
        </h3>
        <div class="row">
            <div id="despesas_chart_div"></div>
            <div id="receita_chart_div"></div>
        </div>
    <table class="table table-striped table-sm">
        
            <%Transacao[] transacoes = BD.getTransacoesMensal(Router.getId());
                                            out.print("<th>Nome</th>");
                                            out.print("<th>Valor</th>");
                                            out.print("<th>Data</th>");
                                            out.print("<th>Categoria</th>");
                                            out.print("<th>Parcela</th>");
                                            int tam = 0;
                                            while (transacoes[tam].getId() != -1){
                                                out.print("<tr>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getNome()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getValor()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getData()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getCategoria()) + "</td>");
                                                out.print("<td>" + String.valueOf(transacoes[tam].getParcela_atual()) + "/" + String.valueOf(transacoes[tam].getParcela_total()) + "</td>");
                                                out.print("</tr>");
                                                tam++;
                                            }
                                        %>
            
        
    </table>  
    </main>
                                        
    <form action="Router" method="get">
        <%BD.atualizaTotal(Router.getId());%>
        <div align="center">
        <button class="btn btn-dark" type="submit" name="path" value="Cadastrar Cartao">Cadastrar Cartao</button>
        <button class="btn btn-dark" type="submit" name="path" value="Registrar Transacao">Registrar Transacao</button>
        </div>
    </form>
    <%
        String despesasMapJson = BD.getDespesasPorCategoria(Router.getId());
        String receitaMapJson = BD.getReceitaPorCategoria(Router.getId());
     %>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript">
        google.charts.load('current', { 'packages': ['corechart'] });
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Categoria');
            data.addColumn('number', 'Saldo');
            var despesaMap = <%= despesasMapJson%> ;
            for (const [key, value] of Object.entries(despesaMap)) {
                var arr = [key, Math.abs(value)];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Despesas por Categoria',
                'width': 400,
                'height': 300
            };
            var chart = new google.visualization.PieChart(document.getElementById('despesas_chart_div'));
            chart.draw(data, options);
        }
    </script>

    <script type="text/javascript">
        google.charts.load('current', { 'packages': ['corechart'] });
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Categoria');
            data.addColumn('number', 'Saldo');
            var receitaMap = <%= receitaMapJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value)];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Receita por Categoria',
                'width': 400,
                'height': 300
            };
            var chart = new google.visualization.PieChart(document.getElementById('receita_chart_div'));
            chart.draw(data, options);
        }
    </script>
</body>
</html>