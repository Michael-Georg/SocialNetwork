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
    <h1>${registration}</h1>
    <form name="signUp" action="SignUp" method="post">
        <section class="part one">
            <p><label for="firstName">${firstName}</label>
                <input id="firstName" name="firstName" pattern="[A-Za-zА-Яа-я]{2,}" required><span>123</span></p>

            <p><label for="lastName">${lastName}</label>
                <input id="lastName" name="lastName" pattern="[A-Za-zА-Яа-я]{2,}" required></p>

            <p><label for="email">${mail}</label>
                <input id="email" name="email" pattern="[\w-\.]+@(\w+\.)+[A-Za-z]{2,4}" required></p>

            <p><label for="password">Пароль</label>
                <input id="password" type="password" name="password" autocomplete="off" required/>
        </section>

        <section class="part two">
            <button type="submit">${signUp}</button>
        </section>
    </form>
</div>
</body>
</html>
