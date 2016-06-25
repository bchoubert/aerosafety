<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="../layout/beforeContent.jsp"></jsp:include>
<div class="container">
	<h1>Inscription</h1>
	
	<c:if test="${empty user}">
	<form action="registerValidate.htm" method="get" class="col-xs-6 col-xs-offset-3">
		<input name="email" class="col-xs-12" placeholder="Adresse e-mail"
			type="email" required /> <input name="firstname" class="col-xs-12"
			placeholder="Pr�nom" type="text" required /> <input name="lastname"
			class="col-xs-12" placeholder="Nom" type="text" required /> <input
			name="password" class="col-xs-12" placeholder="Mot de passe"
			type="password" autocomplete="off" required /> <input type="submit"
			class="btn btn-primary col-xs-12 MarginLeft0 MarginRight0" />
	</form>
	<a href="login.htm" class="col-xs-12 Tcenter">Vous avez un compte ?</a>
	</c:if>
	
	<c:if test="${!empty user}">
	<a>Votre compte a �t� cr�� avec succ�s, ${user.forname} ${user.surname}. </a>
	<br/><br/>
	<a href="logout.htm">Se d�connecter?</a>
	</c:if>
	

</div>
<jsp:include page="../layout/afterContent.jsp"></jsp:include>