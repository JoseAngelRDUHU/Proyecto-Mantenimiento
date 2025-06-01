<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
body {
    font-family: 'Segoe UI', Tahoma, sans-serif;
    background-color: #f9fafb;
    color: #333;
    padding: 40px;
    max-width: 900px;
    margin: auto;
    line-height: 1.6;
}

h1, h2 {
    color: #2c3e50;
    margin-bottom: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    background-color: #ffffff;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.05);
    border-radius: 6px;
    overflow: hidden;
}

th, td {
    padding: 12px 16px;
    border-bottom: 1px solid #e5e7eb;
    text-align: left;
}

th {
    background-color: #f3f4f6;
    color: #374151;
    font-weight: 600;
}

tr:hover {
    background-color: #f9fafc;
}

a {
    color: #007bff;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}

form {
    display: inline;
}

input[type="text"] {
    padding: 6px 10px;
    margin-right: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

button[type="submit"] {
    background-color: #10b981;
    color: white;
    border: none;
    padding: 8px 14px;
    margin-top: 5px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
    font-size: 14px;
}

button[type="submit"]:hover {
    background-color: #059669;
}

form button:last-child {
    background-color: #ef4444;
}

form button:last-child:hover {
    background-color: #dc2626;
}

label {
    display: block;
    margin-top: 10px;
    font-weight: 500;
}

form input, form:input {
    margin-top: 5px;
    width: 100%;
    max-width: 400px;
}

</style>

<c:if test="${isAdmin}">
    <h1><spring:message code="category.management.title"/></h1>
</c:if>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th><spring:message code="category.table.header.name"/></th>
            <th><spring:message code="category.table.header.description"/></th>
            <c:if test="${isAdmin}">
                <th><spring:message code="category.table.header.actions"/></th>
            </c:if>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/article/list.do?categoryId=${category.id}">
                        <c:out value="${category.name}" /></a></td>
                <td>${category.description}</td>
                <c:if test="${isAdmin}">
                    <td>
                        <form action="${pageContext.request.contextPath}/category/edit.do" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${category.id}"/>
                            <input type="text" name="name" value="${category.name}" required/>
                            <input type="text" name="description" value="${category.description}" required/>
                            <button type="submit"><spring:message code="category.form.save"/></button>
                        </form>

                        <form action="${pageContext.request.contextPath}/category/delete.do" method="post" style="display:inline;"
                              onsubmit="return confirm('<spring:message code="category.confirm.delete"/>');">
                            <input type="hidden" name="categoryId" value="${category.id}"/>
                            <button type="submit"><spring:message code="category.form.delete"/></button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </tbody>
</table>

<c:if test="${isAdmin}">
    <h2><spring:message code="category.add.new.title"/></h2>
    <form:form method="post" action="${pageContext.request.contextPath}/category/add.do" modelAttribute="newCategory">
        <label for="name"><spring:message code="category.form.label.name"/></label>
        <form:input path="name" id="name" required="true" />
        <br/>
        <label for="description"><spring:message code="category.form.label.description"/></label>
        <form:input path="description" id="description" required="true" />
        <br/>
        <button type="submit"><spring:message code="category.form.button.add"/></button>
    </form:form>
</c:if>