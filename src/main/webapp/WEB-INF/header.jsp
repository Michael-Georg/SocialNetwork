<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <title>header</title>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="signOut" var="signOut"/>

</head>
<body>
<header>
    <a href="/Location">${sessionScope.lang}</a>
    <a href="/SignOut">${signOut}</a>
    <h1>${person.firstName} ${person.lastName}</h1>
</header>

</body>
</html>