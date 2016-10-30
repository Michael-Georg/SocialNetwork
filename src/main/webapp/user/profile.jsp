<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<html>
<head>
    <title>Profile</title>
    <style type="text/css">
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
<aside>
    <jsp:include page="/WEB-INF/sidebar.jsp"/>
</aside>
    <section>
        <jsp:useBean id="user" scope="request" type="models.Person"/>
        ${user.firstName}
        ${user.lastName}

    </section>
</div>
</body>
</html>