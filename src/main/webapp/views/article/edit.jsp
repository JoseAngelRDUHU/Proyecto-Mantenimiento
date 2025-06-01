<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<style>
body {
    font-family: 'Segoe UI', Tahoma, sans-serif;
    background-color: #f4f6f8;
    padding: 30px;
    color: #333;
}

h2 {
    text-align: center;
    color: #2c3e50;
    font-size: 24px;
    margin-bottom: 30px;
}

form {
    max-width: 600px;
    margin: 0 auto;
    background-color: #ffffff;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

div {
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 6px;
    font-weight: bold;
    color: #444;
}

input[type="text"],
input[type="url"],
textarea,
select {
    width: 100%;
    padding: 10px 12px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
    transition: border-color 0.3s ease;
}

textarea {
    height: 120px;
    resize: vertical;
}

input:focus,
textarea:focus,
select:focus {
    border-color: #007bff;
    outline: none;
}

input[type="checkbox"] {
    margin-right: 8px;
}

input[type="submit"] {
    background-color: #007bff;
    color: white;
    padding: 10px 18px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

input[type="submit"]:hover {
    background-color: #0056b3;
}

</style>

<h2>
    <c:choose>
        <c:when test="${article.id == 0}">
            <spring:message code="edit.title.new" />
        </c:when>
        <c:otherwise>
            <spring:message code="edit.title.edit" />
        </c:otherwise>
    </c:choose>
</h2>

<form:form modelAttribute="article" action="${pageContext.request.contextPath}/article/save.do" method="post">
    <form:hidden path="id" />
    <form:hidden path="version" />

    <div>
        <label><spring:message code="edit.label.title" /></label>
        <form:input path="title" />
    </div>

    <div>
        <label><spring:message code="edit.label.body" /></label>
        <form:textarea path="body" />
    </div>

    <div>
        <label><spring:message code="edit.label.category" /></label>
        <form:select path="category.id">
            <form:options items="${categories}" itemValue="id" itemLabel="name" />
        </form:select>
    </div>

    <div>
        <label><spring:message code="edit.label.allowComments" /></label>
        <form:checkbox path="commentsEnabled" />
    </div>

    <div>
        <label><spring:message code="edit.label.coverImageUrl" /></label>
        <form:input path="coverImageUrl" />
    </div>

    <div>
        <label><spring:message code="edit.label.videoLink" /></label>
        <form:input path="videoLink" />
    </div>

    <div>
        <input type="submit" value="<spring:message code='edit.button.save' />" />
    </div>
</form:form>