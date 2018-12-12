<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error report</title>
<style>
<!--
H1 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 22px;
}

H2 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 16px;
}

H3 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 14px;
}

BODY {
	font-family: Tahoma, Arial, sans-serif;
	color: black;
	background-color: white;
}

B {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
}

P {
	font-family: Tahoma, Arial, sans-serif;
	background: white;
	color: black;
	font-size: 13px;
}

A {
	color: black;
}

A.name {
	color: black;
}

HR {
	color: #525D76;
}
-->
</style>
</head>
<body>
	<h1>HTTP Status 500 -</h1>
	<HR size="1" noshade="noshade">
	<p>
		<b>type</b> Status report
	</p>
	<p style="line-height: 1.8;">
		<c:set var="debugAllowIP" value='<%=this.getServletContext().getInitParameter("debugAllowIP")%>' />
		<c:set var="clientIP" value='<%=request.getRemoteAddr()%>' />
		<c:if test="${clientIP == '0:0:0:0:0:0:0:1' || clientIP == '127.0.0.1' || clientIP == debugAllowIP}">
			<b>message</b> <u><%=exception.toString()%></u><br>
			<c:forEach items="${exception.stackTrace}" var="element">
			    <c:out value="${element}" /><br>
			</c:forEach>
		</c:if>
	</p>
	<p>
		<b>description</b> <u>Internal Server Error.</u>
	</p>
	<HR size="1" noshade="noshade">
</body>
</html>
