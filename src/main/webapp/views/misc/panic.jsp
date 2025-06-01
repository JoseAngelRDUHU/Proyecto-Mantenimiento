<%--
 * panic.jsp
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

<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
/* Base page styling */
body {
    font-family: Arial, sans-serif;
    background-color: #fff4f4;
    color: #5a0000;
    padding: 40px;
}

/* Headings */
h2 {
    color: #a00000;
    margin-top: 30px;
}

/* Code blocks */
code, p[style*="Courier New"] {
    font-family: 'Courier New', Courier, monospace;
    background-color: #fbeaea;
    display: block;
    padding: 10px;
    border-left: 4px solid #d00000;
    white-space: pre-wrap;
    overflow-x: auto;
    margin-top: 10px;
    margin-bottom: 20px;
    color: #3a0000;
}

/* Paragraphs */
p {
    font-size: 16px;
}

</style>

<p><spring:message code="panic.text" /> <code>${name}</code>.</p>

<h2><spring:message code="panic.message" /></h2>

<p style="font-family: 'Courier New'">
	${exception}
</p>

<h2><spring:message code="panic.stack.trace" /></h2>

<p style="font-family: 'Courier New'">	
	${stackTrace}
</p>