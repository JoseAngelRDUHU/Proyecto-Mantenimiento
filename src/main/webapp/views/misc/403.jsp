<%--
 * 403.jsp
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
/* Page base */
body {
    font-family: Arial, sans-serif;
    background-color: #fff6f6;
    color: #4d0000;
    margin: 40px;
    text-align: center;
}

/* Message */
p {
    font-size: 18px;
    margin: 20px 0;
}

/* Link styling */
a {
    color: #990000;
    text-decoration: none;
    font-weight: bold;
    font-size: 16px;
}

a:hover {
    text-decoration: underline;
}

</style>

<p>Oops! You don't have access to this resource.</p> 

<p><a href="<spring:url value='/' />">Return to the welcome page</a><p>
