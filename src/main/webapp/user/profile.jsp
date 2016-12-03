<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<jsp:useBean id="user" scope="request" type="models.Person"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="dob" var="dob"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="telephone" var="telephone"/>
    <fmt:message bundle="${loc}" key="address" var="address"/>
    <fmt:message bundle="${loc}" key="wall.add" var="add"/>
    <fmt:message bundle="${loc}" key="wall.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="wall.comments" var="comments"/>
    <fmt:message bundle="${loc}" key="follow" var="follow"/>
    <fmt:message bundle="${loc}" key="unfollow" var="unfollow"/>
    <fmt:message bundle="${loc}" key="page.blockMsg" var="blockMsg"/>
    <fmt:message bundle="${loc}" key="wall.startMsg" var="startMsg"/>
    <script src="${pageContext.request.contextPath}/js/websocket.js"></script>
</head>
<body data-smsg="${add}" data-rmsg="${cancel}" data-cmsg="${comments}" data-relation="${requestScope.relationStatus}">
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
    <div class="page_layout">
        <jsp:include page="/WEB-INF/sidebar.jsp"/>
        <div class="fl_r">
            <div class="page_body">
                <div class="avatar_block">
                    <div class="page_block">
                        <div class="avatar_wrap">
                            <div>
                                <img class="page_avatar" src='/images/${user.id}.jpg'/>
                            </div>
                            <c:if test="${person.id ne user.id}">
                                <form class="only_button" action="${pageContext.request.contextPath}/AddRemoveFriend"
                                      method="get">
                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                    <c:choose>
                                        <c:when test="${requestScope.relationStatus eq 2}">
                                            <button class="avatar_button" type="submit" name="status" value="0">
                                                    ${unfollow}
                                            </button>
                                        </c:when>
                                        <c:when test="${requestScope.relationStatus eq 0}">
                                            <button class="avatar_button" type="submit" name="status" value="2">
                                                    ${follow}
                                            </button>
                                        </c:when>
                                    </c:choose>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="main_section">
                    <c:choose>
                        <c:when test="${requestScope.relationStatus eq 1}">
                            <div class="page_block fl_l">
                                <div class="avatar_wrap">
                                    <h2 class="page_name"> ${user.firstName} ${user.lastName}</h2>
                                    <h1>${blockMsg}</h1>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="page_block fl_l">
                                <div class="avatar_wrap">
                                    <div>
                                        <h2 class="page_name"> ${user.firstName} ${user.lastName}</h2>
                                    </div>
                                    <div class="profile_info">
                                        <c:if test="${not empty user.dob}">
                                            <div class="info_line">
                                                <div class="label fl_l">
                                                        ${dob}:
                                                </div>
                                                <div>
                                                        ${user.dob}
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty user.telephone}">
                                            <div class="info_line">
                                                <div class="label fl_l">
                                                        ${telephone}:
                                                </div>
                                                <div>
                                                        ${user.telephone}
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty user.email}">
                                            <div class="info_line">
                                                <div class="label fl_l">
                                                        ${mail}:
                                                </div>
                                                <div>
                                                        ${user.email}
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty user.address}">
                                            <div class="info_line">
                                                <div class="label fl_l">
                                                        ${address}:
                                                </div>
                                                <div>
                                                        ${user.address}
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty user.info}">
                                            <div class="info_line">
                                                <div class="label fl_l">
                                                    info:
                                                </div>
                                                <div>
                                                        ${user.info}
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${person.id eq user.id}">
                                <div class="page_block fl_l">
                                    <div id="addMsg">
                                        <button id="msg_start_button" class="msg_start" onclick=showForm()>${startMsg}</button>
                                        <form id="msgForm" class="message-form" action="">
                                            <input type="hidden" name="userId" value="${person.id}">
                                            <label for="text"></label><textarea name="text" id="text"
                                                                                class="settings-info"
                                                                                maxlength="255"></textarea>
                                            <input type="button" class="post-button" value="${add}"
                                                   onclick=startFormSubmit()>
                                            <input type="reset" class="post-button" value="${cancel}"
                                                   onclick=hideForm()>
                                        </form>
                                    </div>
                                    <br/>
                                </div>
                            </c:if>
                            <div id="content">
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <div class="footer"></div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>