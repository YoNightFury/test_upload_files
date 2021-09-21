<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Files List</title>
</head>
<body>
	<c:forEach var="name" items="${names}" >
	<a href="show/${name}" >${name}</a> <br/>
	</c:forEach>


</body>
</html>