<%@ page import="connection.ConexaoMySQL"%>
<%@ page import="connection.BD"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.lang.*"%>

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
<link rel="stylesheet" type="text/css" href="css/graficos.css">
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

</head>
<body>
	<h3>
		Saldo Liquido dos últimos 6 meses:
		<%=BD.getSaldoTotalSeisMeses(Router.getId())%></h3>
	<div id="saldo_seis_div"></div>

	<div id="receita_seis_div"></div>

	<div id="despesa_seis_div"></div>
	<% out.println(Router.getId()); %>

	<%
		String receitaSeisMesesJson = BD.getReceitaSeisMeses(Router.getId());
		String despesaSeisMesesJson = BD.getDespesasSeisMeses(Router.getId());
		String saldoSeisMesesJson = BD.getSaldoSeisMeses(Router.getId());
	%>


	<script type="text/javascript">
	    google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChartReceita);
        google.charts.setOnLoadCallback(drawChartDespesa);
        google.charts.setOnLoadCallback(drawChartSaldo);

        function drawChartReceita() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Mes');
            data.addColumn('number', 'Receita');
            var receitaMap = <%=receitaSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value)];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Receita por Mês (Últimos 6 meses)',
                'width': 1000,
                'height': 300,
                'legend': {position: 'none'}
            };
            var chart = new google.visualization.ColumnChart(document.getElementById('receita_seis_div'));
            chart.draw(data, options);
        }
        function drawChartDespesa() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Mes');
            data.addColumn('number', 'Despesa');
            data.addColumn({type:'string', role:'style'});  // interval role col.
            var receitaMap = <%=despesaSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value),"red"];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Despesa por Mês (Últimos 6 meses)',
                'width': 1000,
                'height': 300,
                'legend': {position: 'none'}
            };
            var chart = new google.visualization.ColumnChart(document.getElementById('despesa_seis_div'));
            chart.draw(data, options);
        }
        function drawChartSaldo() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Mes');
            data.addColumn('number', 'Saldo');
            data.addColumn({type:'string', role:'style'}); 
            var receitaMap = <%=saldoSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value),"green"];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Saldo por Mês (Últimos 6 meses)',
                'width': 1000,
                'height': 300,
                'legend': {position: 'none'}
            };
            var chart = new google.visualization.ColumnChart(document.getElementById('saldo_seis_div'));
            chart.draw(data, options);
        }
    </script>
</body>
</html>