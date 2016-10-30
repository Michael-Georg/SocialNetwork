<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <%--<style type="text/css">--%>
    <%--<%@ include file="/css/styles.css" %>--%>
    <%--</style>--%>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="registration" var="registration"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="password" var="password"/>
    <fmt:message bundle="${loc}" key="signIn" var="signIn"/>
    <fmt:message bundle="${loc}" key="errMsg" var="errMsg"/>
</head>
<body>
<div id="wrapper">
    <form name="login" method="post" action="Login">
        <input class="reg" name="username" title="Login" placeholder="${mail}"/>
        <input class="reg" placeholder="${password}" type="password" name="password" autocomplete="off"
               title="Password"/>
        <button type="submit">${signIn}</button>
    </form>
    <h1>
        <c:if test='${requestScope.containsKey("error")}'>
            ${errMsg}
        </c:if>
    </h1>
    <a href="/Login">${registration}</a>
</div>

</body>
</html>