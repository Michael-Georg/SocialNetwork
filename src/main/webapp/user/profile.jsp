<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"/>
</head>
<body>
<div id="wrapper">
    <header>
        <h1>${person.firstName} ${person.lastName}</h1>
    </header>
    <aside>
        <p><img src="${pageContext.request.contextPath}/images/ALF.jpg" width="300" height="200" alt=""/></p>
        <nav>
            <ul class="aside-menu">
                <li><a href="Profile">Моя страница</a></li>
                <li><a href="Profile">Друзья</a></li>
                <li><a href="Profile">Сообщения</a></li>
                <li><a href="Profile">Музыка</a></li>
            </ul>
        </nav>
    </aside>
    <section></section>
</div>

</body>
</html>
