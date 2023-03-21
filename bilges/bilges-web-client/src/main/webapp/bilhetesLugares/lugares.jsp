<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.CompraBLugaresModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>Bilges: Compra Bilhetes com Lugares</title>
</head>
<body>
<h2>Indique os lugares que quer escolher:</h2>
<form action="comprarLugares" method="post">

	<h3>Evento: ${model.nomeEvento}</h3> 
	<input type="hidden" name="evento" value="${model.nomeEvento}"/>
	
	<h3>Data: ${model.dataEscolhida}</h3> 
	<input type="hidden" name="evento" value="${model.nomeEvento}"/>

	<fieldset>
			<legend>Escolher lugares</legend>
	
			
			
			<label for="lugar">Lugares disponíveis:</label> <br/>
			
				<c:forEach var="lugar" items="${model.lugares}">
				
					<input type="checkbox" name="lugarEscolhido" value="${lugar}">${lugar} <br>
	
				</c:forEach> 
				

    			<label for="email">Email:</label> 
    			<input type="text" name="email" size="100" value="${model.email}"/> 

					
			<div class="button" align="right">
   				<input type="submit" value="Comprar">
			</div>
			 
   </fieldset>
</form>

<c:if test="${model.hasMessages}">
	<p>Mensagens</p>
	<ul>
	<c:forEach var="mensagem" items="${model.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
</body>
</html>