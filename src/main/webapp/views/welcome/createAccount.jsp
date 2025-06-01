<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
/* General page and heading */
body {
    font-family: Arial, sans-serif;
    background-color: #fdfdfd;
    color: #333;
    margin: 30px;
}

h2 {
    color: #003366;
    font-size: 24px;
    margin-bottom: 20px;
}

/* Form styling */
form {
    max-width: 400px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
}

/* Labels */
label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

/* Inputs */
input[type="text"],
input[type="email"],
input[type="password"] {
    width: 100%;
    padding: 8px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

/* Submit button */
button[type="submit"] {
    background-color: #003366;
    color: white;
    padding: 10px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
}

button[type="submit"]:hover {
    background-color: #0055aa;
}

</style>


<h2><spring:message code="editorForm.title" text="Crear Nuevo Editor" /></h2>

<form action="${pageContext.request.contextPath}/welcome/createAccount.do" method="post">
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
</form>