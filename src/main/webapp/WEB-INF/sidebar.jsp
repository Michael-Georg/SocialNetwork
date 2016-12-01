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
        <fmt:message bundle="${loc}" key="followers" var="followers"/>
        <fmt:message bundle="${loc}" key="following" var="following"/>
        <fmt:message bundle="${loc}" key="blackList" var="blackList"/>
        <fmt:message bundle="${loc}" key="audio" var="audio"/>
        <fmt:message bundle="${loc}" key="settings" var="settings"/>
        <fmt:message bundle="${loc}" key="signOut" var="signOut"/>
    </head>
    <body>
        <div class="fl_l">
            <div class="sidebar">
                <div class="page_block">
                    <div class="avatar_wrap">
                        <a href="/Profile/${person.id}">
                            <img class="img_circle" src="/images/${person.id}.jpg"/>
                        </a>
                    </div>
                    <nav>
                        <ul class="side_menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/Followers">${followers}</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/Following">${following}</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/BlackList">${blackList}</a>
                            </li>
                            <%--<li>--%>
                                <%--<a href="${pageContext.request.contextPath}/user/messages.jsp">${audio}</a>--%>
                            <%--</li>--%>
                            <li>
                                <a href="${pageContext.request.contextPath}/Settings">${settings}</a>
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