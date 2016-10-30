<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>header</title>
    <style type="text/css">
        <%@ include file="/css/styles.css" %>
    </style>

</head>
<body>
<header>
    <a href="/location">${sessionScope.lang}</a>
    <h1>${person.firstName} ${person.lastName}</h1>
</header>

</body>
</html>