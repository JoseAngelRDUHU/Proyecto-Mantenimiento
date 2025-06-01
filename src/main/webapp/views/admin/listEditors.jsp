<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<style>
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f5f7fa;
    padding: 30px;
    color: #333;
}

h2 {
    text-align: center;
    color: #2c3e50;
    font-size: 24px;
    margin-bottom: 25px;
}

p {
    text-align: center;
    margin-bottom: 20px;
}

button {
    padding: 8px 14px;
    margin: 3px;
    font-size: 13px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    background-color: #007bff;
    color: white;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #0056b3;
}

button[type="submit"] {
    background-color: #dc3545;
}

button[type="submit"]:hover {
    background-color: #c82333;
}

table {
    width: 90%;
    margin: 0 auto;
    border-collapse: collapse;
    background-color: white;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    overflow: hidden;
}

thead {
    background-color: #007bff;
    color: white;
}

thead th {
    padding: 12px;
    text-align: left;
    font-weight: normal;
    font-size: 14px;
}

tbody td {
    padding: 12px;
    border-top: 1px solid #eee;
    font-size: 14px;
}

tbody tr:hover {
    background-color: #f1f1f1;
}

a {
    text-decoration: none;
}

</style>

<h2><spring:message code="listEditors.title" text="Listado de editores" /></h2>

<!-- Botón para crear nuevo editor -->
<p>
    <a href="${pageContext.request.contextPath}/admin/createEditor.do">
        <button><spring:message code="listEditors.button.create" text="Crear nuevo editor" /></button>
    </a>
</p>

<table border="1" cellpadding="5" cellspacing="0">
    <thead>
        <tr>
            <th><spring:message code="listEditors.column.name" text="Nombre" /></th>
            <th><spring:message code="listEditors.column.email" text="Email" /></th>
            <th><spring:message code="listEditors.column.phone" text="Teléfono" /></th>
            <th><spring:message code="listEditors.column.username" text="Usuario" /></th>
            <th><spring:message code="listEditors.column.actions" text="Acciones" /></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="editor" items="${editors}">
            <tr>
                <td><c:out value="${editor.name}" /></td>
                <td><c:out value="${editor.email}" /></td>
                <td><c:out value="${editor.phone}" /></td>
                <td><c:out value="${editor.userAccount.username}" /></td>
                <td>
                    <!-- Ver artículos -->
                    <a href="${pageContext.request.contextPath}/article/myList.do?authorId=${editor.id}">
                        <button><spring:message code="listEditors.button.viewArticles" text="Ver Artículos" /></button>
                    </a>

                    <!-- Editar -->
                    <a href="${pageContext.request.contextPath}/admin/editEditor.do?editorId=${editor.id}">
                        <button><spring:message code="listEditors.button.edit" text="Editar" /></button>
                    </a>

                    <!-- Borrar -->
                    <form action="${pageContext.request.contextPath}/admin/deleteEditor.do" method="post" style="display:inline;">
                        <input type="hidden" name="editorId" value="${editor.id}" />
                        <button type="submit" onclick="return confirm('<spring:message code="listEditors.confirm.delete" text="¿Estás seguro de que quieres borrar este editor?" />');">
                            <spring:message code="listEditors.button.delete" text="Borrar" />
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>