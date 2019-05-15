<%@ page import="connection.ConexaoMySQL"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
         url = "jdbc:mysql://remotemysql.com:3306/IJWndC9XfP?useSSL=false&amp;"
         user = "IJWndC9XfP"  password = "a0aCdB3CHa"/>
 
      <sql:query dataSource = "${snapshot}" var = "result">
         SELECT * from Usuario;
      </sql:query>
      
       <c:forEach var = "row" items = "${result.rows}">
            <tr>
               <td><c:out value = "${row.nome}"/></td>
            </tr>
         </c:forEach>
</body>
</html>