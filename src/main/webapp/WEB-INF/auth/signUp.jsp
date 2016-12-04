<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">
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
            <a href="${pageContext.request.contextPath}/Location">${sessionScope.lang}</a>
        </div>
    </header>
    <form class="form-signup" name="signUp" action="SignUp" method="post">
        <h1>${signUp}</h1>
        <div class="settings-line">
            <label class="settings-label" for="firstName">${firstName}</label>
            <input class="settings-input" id="firstName" name="firstName"
                   maxlength="20" pattern="[A-Za-zА-Яа-я]{2,}" required>
            <span></span>
        </div>
        <div class="settings-line">
            <label class="settings-label" for="lastName">${lastName}</label>
            <input class="settings-input" id="lastName" name="lastName"
                   maxlength="20" pattern="[A-Za-zА-Яа-я]{2,}" required>
            <span></span>
        </div>
        <div class="settings-line">
            <label class="settings-label" for="email">${mail}</label>
            <input class="settings-input" id="email" name="email"
                   maxlength="30" pattern="[a-zA-Z0-9_\.\+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-\.]{2,}" required>
            <span></span>
        </div>
        <div  class="settings-line">
            <label class="settings-label" for="password">${password}</label>
            <input class="settings-input" id="password" type="password"
                   maxlength="255" name="password" autocomplete="off"/>
        </div>
        <span></span>
        <div class="errmsg settings-line">
            <c:if test='${requestScope.error eq "errEmail"}'>
                Email already used
            </c:if>
        </div>
        <div>
            <button class="setting-button" type="submit">${signUp}</button>
        </div>
    </form>
</div>
</body>
</html>
