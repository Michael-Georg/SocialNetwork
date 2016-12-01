<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="signUp" var="signUp"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="password" var="password"/>
    <fmt:message bundle="${loc}" key="signIn" var="signIn"/>
    <fmt:message bundle="${loc}" key="login.errMsg" var="errMsg"/>
</head>
<body>
<div id="wrapper">
    <header>
        <div class="lang">
            <a href="Location">${sessionScope.lang}</a>
        </div>
    </header>
    <div class="form-signin">
        <form name="login" method="post" action="/Login">

            <input id="email" class="reg" name="email" title="Login" placeholder="${mail}"/>

            <input class="reg" placeholder="${password}" type="password" name="password" autocomplete="off"
                   title="Password"/>

            <div class="errmsg">
                <c:if test='${requestScope.containsKey("error")}'>
                    ${errMsg}
                </c:if>
            </div>
            <button class="reg_button" type="submit">${signIn}</button>
            <div class="reglink">
                <a href="SignUp">${signUp}</a>
            </div>
        </form>

    </div>
</div>
</body>
</html>