<!DOCTYPE html>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>header</title>
    <%--<link rel="stylesheet" href="/css/styles.css" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="search" var="search"/>
    <fmt:message bundle="${loc}" key="search.msg" var="searchMsg"/>
</head>
<body>
<header>
    <div class="lang">
        <a href="/Location">${sessionScope.lang}</a>
    </div>
    <div class="lang">
        <form class="search-form" action="/Search" method="post">
            <input type="text" class="search-input" placeholder="${searchMsg}" name="mask">
            <button class="search-button" type="submit">${search}</button>
        </form>
    </div>
    <div class="fio inline">
        ${person.firstName} ${person.lastName}
    </div>
</header>
</body>
</html>