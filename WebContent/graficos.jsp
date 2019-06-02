<%@page import="util.TransacaoPorMesCategoria"%>
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
			<li class="nav-item"><a class="nav-link" href="mainPage.jsp">
					Inicio </a></li>

			<li class="nav-item"><a class="nav-link active"
				href="graficos.jsp">Graficos <span class="sr-only">(current)</span>
			</a></li>
		</ul>

	</div>

	<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
	<h3>
		Saldo Liquido dos últimos 6 meses:
		<%=BD.getSaldoTotalSeisMeses(Router.getId())%></h3>


        <div class="row">
            <div id="despesa_cat_seis_div"></div>
            <div id="receita_cat_seis_div"></div>
        </div>
<div>
	<div id="despesa_categoria_div"></div>
</div>

	<div id="saldo_seis_div"></div>

	<div id="receita_seis_div"></div>

	<div id="despesa_seis_div"></div>
        

	<%
		String receitaSeisMesesJson = BD.getReceitaSeisMeses(Router.getId());
		String despesaSeisMesesJson = BD.getDespesasSeisMeses(Router.getId());
		String saldoSeisMesesJson = BD.getSaldoSeisMeses(Router.getId());
		String despesaSeisMesesCategorizado = BD.getDespesaSeisMesesCategorizado(Router.getId());
	%> </main>
	<% 
                String despesaCatSeisMesesJson = BD.getDespesasPorCategoriaSemestre(Router.getId());
                String receitaCatSeisMesesJson = BD.getReceitaPorCategoriaSemestre(Router.getId());
	%>
	</main>

	<script type="text/javascript">
	    google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChartReceita);
        google.charts.setOnLoadCallback(drawChartDespesa);
        google.charts.setOnLoadCallback(drawChartSaldo);
      	google.charts.setOnLoadCallback(drawChartDespesaCategorizado);
        function drawChartDespesaCategorizado(){
        	var data = new google.visualization.DataTable();
        	data.addColumn('string','Mes');
        	data.addColumn('number',"Alimentação");
        	data.addColumn('number',"Casa");
        	data.addColumn('number',"Comunicação");
        	data.addColumn('number',"Despesas Pessoais");
        	data.addColumn('number',"Educação");
        	data.addColumn('number',"Investimento");
        	data.addColumn('number',"Lazer");
        	data.addColumn('number',"Saúde");
        	data.addColumn('number',"Tarifas e Impostos");
        	data.addColumn('number',"Transporte");
        	data.addColumn('number',"Outros");
        	var despesaCategoria = <%=despesaSeisMesesCategorizado%>
        	
       	 	for (i of despesaCategoria){
        		if(!i) break;
        		console.log(i.mes,i.alimentacao,i.casa,i.comunicação,i.pessoais,i.educação,i.investimento,i.lazer,i.saúde,i.tarifas,i.transporte,i.outros);
        		data.addRow([i.mes,Math.abs(i.alimentacao),Math.abs(i.casa),Math.abs(i.comunicação),Math.abs(i.pessoais),Math.abs(i.educação),Math.abs(i.investimento),Math.abs(i.lazer),Math.abs(i.saúde),Math.abs(i.tarifas),Math.abs(i.transporte),Math.abs(i.outros)]);
        	}
       	 var options = {
       	        width: 1000,
       	        height: 400,
       	        legend: { position: 'top', maxLines: 3 },
       	        bar: { groupWidth: '75%' },
       	        isStacked: true,
       	  	   'title': 'Despesas Categorizadas (Últimos 6 meses)'
       	      };
       	var chart = new google.visualization.ColumnChart(document.getElementById('despesa_categoria_div'));
        chart.draw(data, options);
        }
        
        
        google.charts.setOnLoadCallback(drawChartDespesasCatSemestre);
        google.charts.setOnLoadCallback(drawChartReceitasCatSemestre);
      
        function drawChartDespesa() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Mes');
            data.addColumn('number', 'Despesa');
            data.addColumn({type:'string', role:'style'});  // interval role col.
            var receitaMap = <%=despesaSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value),"red"];
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
       
        function drawChartReceita() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Mes');
            data.addColumn('number', 'Receita');
            var receitaMap = <%=receitaSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value)];
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


        function drawChartSaldo() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Mes');
            data.addColumn('number', 'Saldo');
            data.addColumn({type:'string', role:'style'}); 
            var receitaMap = <%=saldoSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value),"green"];
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
        function drawChartDespesasCatSemestre() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Categoria');
            data.addColumn('number', 'Saldo');
            var despesaMap = <%=despesaCatSeisMesesJson%> ;
            for (const [key, value] of Object.entries(despesaMap)) {
                var arr = [key, Math.abs(value)];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Despesas por Categoria nos últmos 6 meses',
                'width': 400,
                'height': 300
            };
            var chart = new google.visualization.PieChart(document.getElementById('despesa_cat_seis_div'));
            chart.draw(data, options);
        }
        function drawChartReceitasCatSemestre() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Categoria');
            data.addColumn('number', 'Saldo');
            var receitaMap = <%=receitaCatSeisMesesJson%> ;
            for (const [key, value] of Object.entries(receitaMap)) {
                var arr = [key, Math.abs(value)];
                console.log(arr);
                data.addRows([arr]);
            }
            var options = {
                'title': 'Receita por Categoria nos últmos 6 meses',
                'width': 400,
                'height': 300
            };
            var chart = new google.visualization.PieChart(document.getElementById('receita_cat_seis_div'));
            chart.draw(data, options);
        }
    </script>
</body>
</html>