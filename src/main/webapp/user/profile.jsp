<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<jsp:useBean id="user" scope="request" type="models.Person"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">

    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="dob" var="dob"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="telephone" var="telephone"/>
    <fmt:message bundle="${loc}" key="address" var="address"/>
    <script src="/js/websocket.js"></script>
</head>
<body data-smsg="SEND" data-rmsg="RESET" data-cmsg="COMMENT">
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
                                <form class="only_button" action="/AddRemoveFriend" method="get">
                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                    <c:choose>
                                        <c:when test="${requestScope.friendStatus eq 1}">
                                            <button class="avatar_button" type="submit" name="status" value="0">
                                                remove
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="avatar_button" type="submit" name="status" value="1">
                                                add
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="main_section">
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
                            <button id="msg_start_button" class="msg_start" onclick=showForm()>Add a msg</button>
                            <form id="msgForm" class="message-form" action="">
                                <input type="hidden" name="userId" value="${person.id}">
                                <textarea name="text" id="text" class="settings-info" title="123123"></textarea>
                                <input type="button" class="post-button" value="Send" onclick=startFormSubmit()>
                                <input type="reset" class="post-button" value="Cancel" onclick=hideForm()>
                            </form>
                        </div>
                        <br/>
                    </div>
                    </c:if>
                    <div id="content">
                    </div>

                    <div class="footer"></div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>