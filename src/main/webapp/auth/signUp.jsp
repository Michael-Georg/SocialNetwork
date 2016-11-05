<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="registration" var="registration"/>
    <fmt:message bundle="${loc}" key="firstName" var="firstName"/>
    <fmt:message bundle="${loc}" key="laseName" var="lastName"/>
    <fmt:message bundle="${loc}" key="signUp" var="signUp"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="password" var="password"/>
    <fmt:message bundle="${loc}" key="signIn" var="signIn"/>
</head>
<body>
<div id="wrapper">
    <header>
        <div class="lang">
            <a href="/Location">${sessionScope.lang}</a>
        </div>
    </header>
    <form class="form-signup" name="signUp" action="SignUp" method="post">
        <h1>${registration}</h1>
        <div>
            <label for="firstName">${firstName}</label>
            <input class="reg" id="firstName" name="firstName" pattern="[A-Za-zА-Яа-я]{2,}" required>
        </div>
        <div>
            <label for="lastName">${lastName}</label>
            <input class="reg" id="lastName" name="lastName" pattern="[A-Za-zА-Яа-я]{2,}" required>
        </div>
        <div>
            <label for="email">${mail}</label>
            <input class="reg" id="email" name="email" pattern="[\w-\.]+@(\w+\.)+[A-Za-z]{2,4}" required>
        </div>
        <div>
            <label for="password">${password}</label>
            <input class="reg" id="password" type="password" name="password" autocomplete="off" required/>
        </div>
        <div>
            <button type="submit">${signUp}</button>
        </div>
    </form>
</div>
</body>
</html>
