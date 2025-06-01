<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f4f6f8;
    padding: 30px;
    color: #333;
}

h2 {
    text-align: center;
    color: #2c3e50;
    margin-bottom: 30px;
    font-size: 24px;
}

form {
    max-width: 500px;
    margin: 0 auto;
    background-color: #ffffff;
    padding: 25px 30px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

label {
    display: block;
    margin-top: 15px;
    margin-bottom: 6px;
    font-weight: bold;
    color: #444;
}

input[type="text"],
input[type="email"],
input[type="password"] {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 14px;
    box-sizing: border-box;
    transition: border-color 0.3s;
}

input:focus {
    border-color: #007bff;
    outline: none;
}

button {
    margin-top: 25px;
    padding: 10px 20px;
    font-size: 14px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    background-color: #007bff;
    color: white;
    transition: background-color 0.3s;
}

button[type="button"] {
    background-color: #6c757d;
    margin-left: 10px;
}

button:hover {
    background-color: #0056b3;
}

button[type="button"]:hover {
    background-color: #5a6268;
}

a {
    text-decoration: none;
}

</style>

<h2><spring:message code="editorForm.title.edit" text="Editar Editor" /></h2>

<form action="${pageContext.request.contextPath}/admin/saveEditor.do" method="post">
    <input type="hidden" name="id" value="${editorEditor.id}" />

    <label for="name"><spring:message code="editorForm.label.name" /></label><br/>
    <input type="text" id="name" name="name" value="${editorEditor.name}" required/><br/><br/>

    <label for="email"><spring:message code="editorForm.label.email" /></label><br/>
    <input type="email" id="email" name="email" value="${editorEditor.email}" required/><br/><br/>

    <label for="phone"><spring:message code="editorForm.label.phone" /></label><br/>
    <input type="text" id="phone" name="phone" value="${editorEditor.phone}" required/><br/><br/>

    <label for="username"><spring:message code="editorForm.label.username" /></label><br/>
    <input type="text" id="username" name="username" value="${editorEditor.userAccount.username}"/><br/><br/>

    <label for="password"><spring:message code="editorForm.password.hint" /></label><br/>
    <input type="password" id="password" name="password" /><br/><br/>

    <button type="submit"><spring:message code="editorForm.button.save" /></button>
    <a href="${pageContext.request.contextPath}/admin/listEditors.do">
        <button type="button"><spring:message code="editorForm.button.cancel" /></button>
    </a>
</form>