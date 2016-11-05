<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="friends" scope="request" type="java.util.List<models.Person>"/>
<html>
<head>

    <title>Friends</title>
    <link rel="stylesheet" href="/css/styles.css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
</head>
<body>
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
    <aside>
        <jsp:include page="/WEB-INF/sidebar.jsp"/>
    </aside>
    <section>

    <table>
        <c:forEach var="user" items="${friends}">
            <tr>
                <td><a href="/Profile/${user.id}"> ${user.firstName} ${user.lastName}</a></td>
            </tr>
        </c:forEach>
    </table>
    </section>
</div>


</div>
</body>
</html>
