<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
/* General page styling */
body {
    font-family: Arial, sans-serif;
    background-color: #ffffff;
    color: #333;
    margin: 20px;
}

/* Greeting messages */
p {
    font-size: 16px;
    margin-bottom: 20px;
}

/* Article list */
ul {
    padding: 0;
    margin: 0;
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    list-style: none;
}

/* Each article */
ul li {
    width: 180px;
    text-align: center;
}

/* Article image */
ul li img {
    width: 100%;
    height: auto;
    border: 1px solid #ccc;
    border-radius: 4px;
}

/* Article title as link */
ul li a {
    text-decoration: none;
    color: #003366;
    display: block;
    margin-top: 8px;
    font-weight: bold;
}

ul li a:hover {
    color: #0055aa;
}

</style>

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>


<ul>
<%
    java.util.Collection articles = (java.util.Collection) request.getAttribute("articles");
    if (articles != null) {
        for (Object obj : articles) {
            domain.Article article = (domain.Article) obj;
            String title = article.getTitle();
            String imageUrl = article.getCoverImageUrl();
            String link = request.getContextPath() + "/article/view.do?articleId=" + article.getId();

            out.println("<li>");
            out.println("  <a href=\"" + link + "\">");
            out.println("    <img src=\"" + imageUrl + "\" alt=\"" + title + "\" />");
            out.println("    " + title);
            out.println("  </a>");
            out.println("</li>");
        }
    }
%>
</ul>


<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p>
