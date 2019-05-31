<%@ page import="connection.ConexaoMySQL"%>
<%@ page import="connection.BD"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ page import = "java.lang.*" %>

<%@ page import="router.Router"%>
<%@ page import="com.google.gson.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix ="sql"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Suas financas</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/mainPage.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

</head>

<body>
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Bem vindo as suas finanças, <%=BD.getNome(Router.getId())%></a>
        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a class="nav-link" href="#">Sign out</a>
            </li>
        </ul>
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
        <%double saldo = BD.getSaldoMensal();%>
        <h3>Saldo mensal:
            <c:choose>
                <c:when test="${saldo}>0}">
                    <span class="saldoPos">R$<%out.println(Math.abs(saldo));%></span>
                </c:when>
                <c:otherwise>
                    <span class="saldoNeg">-R$<%out.println(Math.abs(saldo));%></span>
                </c:otherwise>
            </c:choose>
        </h3>
        <div class="row">
            <div id="despesas_chart_div"></div>
            <div id="receita_chart_div"></div>
        </div>
        <div>
            <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
                url="jdbc:mysql://remotemysql.com:3306/IJWndC9XfP?useSSL=false&amp;" user="IJWndC9XfP"
                password="a0aCdB3CHa" />

            <sql:query dataSource="${snapshot}" var="result">
                SELECT * from Transacao;
            </sql:query>

            <table class="table table-striped table-sm">
                <tr>
                    <th>Nome</th>
                    <th>Valor</th>
                    <th>Categoria</th>
                </tr>
                <c:forEach var="row" items="${result.rows}">
                    <tr>
                        <td>
                            <c:out value="${row.nome}" />
                        </td>
                        <td>
                            <c:out value="${row.valor}" />
                        </td>
                        <td>
                            <c:out value="${row.categoria}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>


    </main>

    <form action="Router" method="get">
        <%BD.atualizaTotal(Router.getId());%>
        <div align="center">
        <button class="btn btn-dark" type="submit" name="path" value="Cadastrar Cartao">Cadastrar Cartao</button>
        <button class="btn btn-dark" type="submit" name="path" value="Registrar Transacao">Registrar Transacao</button>
        </div>>
    </form>
    <%
        String despesasMapJson = BD.getDespesasPorCategoria();
        String receitaMapJson = BD.getReceitaPorCategoria();
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