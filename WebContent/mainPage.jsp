<%@ page import="connection.ConexaoMySQL"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix ="sql"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Cry System</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
</head>

<body>
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Cry System</a>
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
                    Cartões
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="shopping-cart"></span>
                    Gráficos
                </a>
            </li>
        </ul>

    </div>

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
        <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
            url="jdbc:mysql://remotemysql.com:3306/IJWndC9XfP?useSSL=false&amp;" user="IJWndC9XfP"
            password="a0aCdB3CHa" />

        <sql:query dataSource="${snapshot}" var="result">
            SELECT * from Usuario;
        </sql:query>

        <table class="table table-striped table-sm">
            <c:forEach var="row" items="${result.rows}">
                <tr>
                    <td>
                        <c:out value="${row.nome}" />
                    </td>
                </tr>
            </c:forEach>
        </table>

    </main>


    <script type="text/javascript" src="js/bootstrap.js"></script>
</body>

</html>