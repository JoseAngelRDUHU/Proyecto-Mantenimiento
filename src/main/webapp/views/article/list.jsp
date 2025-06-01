<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<style>
body {
    font-family: 'Segoe UI', Tahoma, sans-serif;
    background-color: #f4f6f8;
    padding: 40px;
    color: #333;
}

h2 {
    text-align: center;
    color: #2c3e50;
    margin-bottom: 30px;
    font-size: 24px;
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

ul {
    list-style-type: none;
    padding: 0;
    max-width: 600px;
    margin: 0 auto;
}

li {
    background-color: white;
    margin-bottom: 10px;
    padding: 15px 20px;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
    display: flex;
    justify-content: space-between;
    align-items: center;
}

li a {
    color: #007bff;
    text-decoration: none;
    font-weight: bold;
}

li a:hover {
    text-decoration: underline;
}

small {
    color: #666;
    font-size: 12px;
}

p {
    text-align: center;
    font-style: italic;
    margin-top: 20px;
    color: #555;
}

</style>


<h2><spring:message code="list.title" /></h2>

<form action="${pageContext.request.contextPath}/article/list.do" method="get">
    <input type="text" name="keyword" value="${keyword}"
           placeholder="<spring:message code='list.search.placeholder' />" />

    <c:if test="${not empty categoryFilter}">
        <input type="hidden" name="categoryId" value="${categoryFilter.id}" />
    </c:if>

    <button type="submit">
        <spring:message code="list.search.button" />
    </button>
</form>

<c:if test="${not empty keyword}">
    <p>
        <spring:message code="list.results">
            <spring:argument value="${keyword}" />
        </spring:message>
    </p>
</c:if>

<c:choose>
    <c:when test="${not empty articles}">
        <ul>
            <c:forEach var="a" items="${articles}">
                <li>
                    <a href="${pageContext.request.contextPath}/article/view.do?articleId=${a.id}">
                        <c:out value="${a.title}" />
                    </a>
                    <small> (${a.publicationMoment})</small>
                </li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty categoryFilter}">
            <p>
                <spring:message code="list.noArticles.category">
                    <spring:argument value="${categoryFilter.name}" />
                </spring:message>
            </p>
        </c:if>
        <c:if test="${empty categoryFilter}">
            <p><spring:message code="list.noArticles.any" /></p>
        </c:if>
    </c:otherwise>
</c:choose>