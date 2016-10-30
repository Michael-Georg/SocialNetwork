<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>header</title>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
</head>
<body>
<header>
    <a href="/Location">${sessionScope.lang}</a>
    <h1>${person.firstName} ${person.lastName}</h1>
</header>

</body>
</html>