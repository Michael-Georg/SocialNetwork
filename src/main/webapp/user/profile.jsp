<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"/>
</head>
<body>
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
    <jsp:include page="/WEB-INF/sidebar.jsp"/>
    <section></section>
</div>
</body>
</html>