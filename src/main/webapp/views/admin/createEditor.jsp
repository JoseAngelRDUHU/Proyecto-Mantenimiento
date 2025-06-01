<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
/* Estilos generales para el formulario */
form {
    max-width: 500px;
    margin: 30px auto;
    padding: 25px 30px;
    background-color: #f9f9f9;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    font-family: Arial, sans-serif;
}

/* Títulos */
h2 {
    text-align: center;
    color: #333;
    font-size: 24px;
    margin-bottom: 20px;
}

/* Etiquetas */
label {
    display: block;
    font-weight: bold;
    color: #444;
    margin-bottom: 6px;
}

/* Campos de entrada */
input[type="text"],
input[type="email"],
input[type="password"] {
    width: 100%;
    padding: 10px 12px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
    font-size: 14px;
    transition: border-color 0.3s ease;
}

input:focus {
    border-color: #007bff;
    outline: none;
}

/* Botones */
button {
    padding: 10px 20px;
    margin-top: 10px;
    margin-right: 10px;
    border: none;
    border-radius: 6px;
    background-color: #007bff;
    color: white;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button[type="button"] {
    background-color: #6c757d;
}

button:hover {
    background-color: #0056b3;
}

button[type="button"]:hover {
    background-color: #5a6268;
}

/* Estilo para el enlace que envuelve el botón Cancelar */
a {
    text-decoration: none;
}

</style>

<h2><spring:message code="editorForm.title" text="Crear Nuevo Editor" /></h2>

<form action="${pageContext.request.contextPath}/admin/createEditor.do" method="post">
    <label for="name"><spring:message code="editorForm.label.name" text="Nombre:" /></label><br/>
    <input type="text" id="name" name="name" required/><br/><br/>

    <label for="email"><spring:message code="editorForm.label.email" text="Email:" /></label><br/>
    <input type="email" id="email" name="email" required/><br/><br/>

    <label for="phone"><spring:message code="editorForm.label.phone" text="Teléfono:" /></label><br/>
    <input type="text" id="phone" name="phone" required/><br/><br/>

    <label for="username"><spring:message code="editorForm.label.username" text="Usuario (username):" /></label><br/>
    <input type="text" id="username" name="username" required/><br/><br/>

    <label for="password"><spring:message code="editorForm.label.password" text="Contraseña:" /></label><br/>
    <input type="password" id="password" name="password" required/><br/><br/>

    <button type="submit">
        <spring:message code="editorForm.button.create" text="Crear Editor" />
    </button>
    <a href="${pageContext.request.contextPath}/admin/listEditors.do">
        <button type="button">
            <spring:message code="editorForm.button.cancel" text="Cancelar" />
        </button>
    </a>
</form>