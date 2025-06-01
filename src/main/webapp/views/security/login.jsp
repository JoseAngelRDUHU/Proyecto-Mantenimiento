 <%--
 * login.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>
/* General layout */
body {
    font-family: Arial, sans-serif;
    background-color: #fdfdfd;
    color: #333;
    margin: 40px;
}

/* Form container */
form {
    max-width: 350px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 6px;
    background-color: #f9f9f9;
}

/* Labels */
label, .form-label {
    display: block;
    margin-bottom: 6px;
    font-weight: bold;
}

/* Input fields */
input[type="text"],
input[type="password"] {
    width: 100%;
    padding: 8px;
    margin-bottom: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

/* Submit button */
input[type="submit"] {
    background-color: #003366;
    color: white;
    padding: 10px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
}

input[type="submit"]:hover {
    background-color: #0055aa;
}

/* Error messages */
.error {
    color: #b30000;
    font-size: 14px;
    margin-bottom: 10px;
}

/* Account creation link */
p {
    margin-top: 15px;
    font-size: 14px;
}

p a {
    color: #003366;
    text-decoration: none;
    font-weight: bold;
}

p a:hover {
    text-decoration: underline;
}

</style>


<form:form action="j_spring_security_check" modelAttribute="credentials">

	<form:label path="username">
		<spring:message code="security.username" />
	</form:label>
	<form:input path="username" />	
	<form:errors class="error" path="username" />
	<br />

	<form:label path="password">
		<spring:message code="security.password" />
	</form:label>
	<form:password path="password" />	
	<form:errors class="error" path="password" />
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed" />
		</div>
	</jstl:if>
	
	<input type="submit" value="<spring:message code="security.login" />" />
	<p><spring:message code="security.question" /> <a href="${pageContext.request.contextPath}/welcome/createAccount.do">
	<spring:message code="security.create" /></a></p>
	
</form:form>
