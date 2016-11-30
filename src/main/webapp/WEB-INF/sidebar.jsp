<!DOCTYPE html>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ page contentType="text/html, ;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>sidebar</title>
            <%--<link rel="stylesheet" href="/css/styles.css" type="text/css">--%>
        <fmt:setLocale value="${sessionScope.lang}"/>
        <fmt:setBundle basename="localization.message" var="loc"/>
        <fmt:message bundle="${loc}" key="friends" var="friends"/>
        <fmt:message bundle="${loc}" key="messages" var="messages"/>
        <fmt:message bundle="${loc}" key="music" var="music"/>
        <fmt:message bundle="${loc}" key="signOut" var="signOut"/>
    </head>
    <body>
        <div class="fl_l">
            <div class="sidebar">
                <div class="page_block">
                    <div class="avatar_wrap">
                        <a href="/Person/${person.id}">
                            <img class="img_circle" src="/images/${person.id}.jpg"/>
                        </a>
                    </div>
                    <nav>
                        <ul class="side_menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/Followers">Followers</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/Following">FOLLOWING</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/BlackList">BlackList</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/user/messages.html">${messages}</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/user/messages.jsp">${music}</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/Settings">Редактировать профиль</a>
                            </li>
                            <li>
                                <a href="/SignOut">${signOut}</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>