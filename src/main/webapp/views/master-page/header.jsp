<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
/* General container styling */
body {
    margin: 0;
    font-family: Arial, sans-serif;
    background-color: #fdfdfd;
}

/* Logo container */
div:first-of-type {
    text-align: center;
    padding: 10px 0;
}

/* Main menu list */
#jMenu {
    list-style: none;
    margin: 0;
    padding: 0;
    background-color: #003366;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}

/* First level links */
#jMenu > li {
    position: relative;
}

#jMenu > li > a.fNiv {
    display: block;
    color: #ffffff;
    padding: 14px 20px;
    text-decoration: none;
    font-weight: bold;
    background-color: #003366;
    transition: background-color 0.3s;
}

#jMenu > li > a.fNiv:hover,
#jMenu > li:hover > a.fNiv {
    background-color: #0055a5;
}

/* Submenu */
#jMenu li ul {
    position: absolute;
    top: 100%;
    left: 0;
    min-width: 200px;
    display: none;
    background-color: #ffffff;
    border: 1px solid #ccc;
    z-index: 1000;
}

#jMenu li:hover ul {
    display: bloc

</style>

<div>
    <a href="#"><img src="images/logoNews.png" alt="Acme News Co., Inc." width="300" height="100"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="admin/dashboard.do"><spring:message
								code="master.page.administrator.dashboard"/></a></li>
					<li><a href="admin/listEditors.do"><spring:message
								code="master.page.administrator.listEditors" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
                	<li><a class="fNiv"><spring:message code="master.page.category" /></a>
                		<ul>
                			<li class="arrow"></li>
                			<li><a href="category/list.do"><spring:message
                						code="master.page.category.list" /> </a></li>
                		</ul>
                	</li>
        </security:authorize>

        <security:authorize access="isAuthenticated()">
                        	<li><a class="fNiv"><spring:message code="master.page.article" /></a>
                        		<ul>
                        			<li class="arrow"></li>
                        			<li><a href="article/list.do"><spring:message
                        						code="master.page.article.list" /> </a></li>
                        			<security:authorize access="hasRole('EDITOR')">
                                        <li><a href="article/myList.do"><spring:message
                                        code="master.page.article.myList" /></a></li>
                                    </security:authorize>
                        		</ul>
                        	</li>
                </security:authorize>

		<security:authorize access="isAuthenticated()">
        	<li><a class="fNiv"><spring:message code="master.page.account" /></a>
        		<ul>
        			<li class="arrow"></li>
        			<li><a href="j_spring_security_logout"><spring:message
        						code="master.page.logout" /> </a></li>
        		</ul>
        	</li>
        </security:authorize>

		<security:authorize access="isAnonymous()">
        		<li><a class="fNiv hover-link" href="security/login.do"><spring:message
        						code="master.page.login" /></a>
        		</li>
        </security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

