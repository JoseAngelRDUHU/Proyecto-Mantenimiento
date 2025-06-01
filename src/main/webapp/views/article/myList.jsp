<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<style>
body {
    font-family: 'Segoe UI', Tahoma, sans-serif;
    background-color: #f9fafb;
    color: #333;
    padding: 40px;
}

h2 {
    text-align: center;
    color: #2c3e50;
    margin-bottom: 30px;
}

form {
    text-align: center;
    margin-bottom: 30px;
}

input[type="text"] {
    width: 300px;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 6px;
    margin-right: 10px;
    transition: border-color 0.3s ease;
}

input[type="text"]:focus {
    border-color: #007bff;
    outline: none;
}

button {
    padding: 10px 16px;
    font-size: 14px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #0056b3;
}

p {
    text-align: center;
    font-style: italic;
    color: #555;
    margin-top: 10px;
}

table {
    width: 90%;
    margin: 0 auto;
    border-collapse: collapse;
    background-color: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    border-radius: 8px;
    overflow: hidden;
}

th, td {
    padding: 14px 16px;
    text-align: left;
    border-bottom: 1px solid #eee;
}

th {
    background-color: #f0f2f5;
    color: #333;
    font-weight: bold;
}

tr:hover {
    background-color: #f9f9f9;
}

a {
    color: #007bff;
    text-decoration: none;
    margin-right: 10px;
}

a:hover {
    text-decoration: underline;
}

a:last-child {
    margin-right: 0;
}

.security-create-link {
    display: block;
    text-align: center;
    margin-top: 30px;
    font-weight: bold;
}

</style>

<h2><spring:message code="mylist.title" /></h2>

<form action="${pageContext.request.contextPath}/article/myList.do" method="get">
    <input type="text" name="keyword" value="${keyword}"
           placeholder="<spring:message code='mylist.search.placeholder' />" />

    <c:if test="${not empty authorId}">
        <input type="hidden" name="authorId" value="${authorId}" />
    </c:if>

    <button type="submit">
        <spring:message code="mylist.search.button" />
    </button>
</form>

<c:if test="${not empty keyword}">
    <p>
        <spring:message code="mylist.results">
            <spring:argument value="${keyword}" />
        </spring:message>
    </p>
</c:if>

<table>
    <thead>
        <tr>
            <th><spring:message code="mylist.column.title" /></th>
            <th><spring:message code="mylist.column.date" /></th>
            <th><spring:message code="mylist.column.actions" /></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${articles}" var="a">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/article/view.do?articleId=${a.id}">
                        <c:out value="${a.title}" />
                    </a>
                </td>
                <td>${a.publicationMoment}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/article/edit.do?articleId=${a.id}">
                        <spring:message code="mylist.edit" />
                    </a> |
                    <a href="${pageContext.request.contextPath}/article/delete.do?articleId=${a.id}"
                       onclick="return confirm('<spring:message code='mylist.delete.confirm' />');">
                        <spring:message code="mylist.delete" />
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<br/>

<security:authorize access="hasRole('EDITOR')">
    <a href="${pageContext.request.contextPath}/article/create.do">
        <spring:message code="mylist.create" />
    </a>
</security:authorize>