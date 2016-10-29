<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<html>
<body>
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
    <jsp:include page="/WEB-INF/sidebar.jsp"/>
    <section>
        <jsp:useBean id="user" scope="request" type="models.Person"/>
        ${user.firstName}
        ${user.lastName}

    </section>
</div>
</body>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"/>
</head>
</html>