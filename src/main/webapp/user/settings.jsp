<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
    <title>setings</title>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="dob" var="dob"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="telephone" var="telephone"/>
    <fmt:message bundle="${loc}" key="address" var="address"/>
    <fmt:message bundle="${loc}" key="registration" var="registration"/>
    <fmt:message bundle="${loc}" key="firstName" var="firstName"/>
    <fmt:message bundle="${loc}" key="laseName" var="lastName"/>
    <fmt:message bundle="${loc}" key="signUp" var="signUp"/>
    <fmt:message bundle="${loc}" key="email" var="mail"/>
    <fmt:message bundle="${loc}" key="password" var="password"/>
    <fmt:message bundle="${loc}" key="signIn" var="signIn"/>
    <fmt:message bundle="${loc}" key="upload" var="upload"/>
    <fmt:message bundle="${loc}" key="info" var="info"/>

</head>
<body>
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
    <div class="page_layout">
        <jsp:include page="/WEB-INF/sidebar.jsp"/>
        <div class="fl_r">
            <div class="page_body">
                <div class="avatar_block">
                    <div class="page_block fl_r">
                        <div class="avatar_wrap">
                            <div>
                                <img class="page_avatar"
                                     src='/images/${person.id}.jpg'/>
                            </div>
                            <form class="only_button" action="/Upload" method="get">
                                <button class="avatar_button" type="submit">${upload}</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="main_section">
                    <div class="page_block fl_l">
                        <form class="form-settings" name="signUp" action="/Settings" method="post">
                            <h1>${registration}</h1>
                            <div class="settings-line">
                                <label class="settings-label" for="firstName">${firstName}</label>
                                <div class="settings-input-wrap">
                                    <input class="settings-input" id="firstName" name="firstName"
                                           pattern="[A-Za-zА-Яа-я]{2,}" value="${person.firstName}">
                                </div>
                            </div>
                            <div class="settings-line">
                                <label class="settings-label" for="lastName">${lastName}</label>
                                <div class="settings-input-wrap">
                                    <input id="lastName" name="lastName" class="settings-input"
                                           pattern="[A-Za-zА-Яа-я]{2,}" value="${person.lastName}">
                                </div>
                            </div>
                            <div class="settings-line">
                                <label class="settings-label" for="dob">${dob}</label>
                                <div class="settings-input-wrap">
                                    <input class="settings-input" id="dob" type="date" name="dob" value="${person.dob}">
                                </div>
                            </div>
                            <div class="settings-line">
                                <label class="settings-label" for="address">${address}</label>
                                <div class="settings-input-wrap">
                                    <input class="settings-input" id="address" type="date"
                                           name="address" value="${person.address}">
                                </div>
                            </div>
                            <div class="settings-line">
                                <label class="settings-label" for="telephone">${telephone}</label>
                                <div class="settings-input-wrap">
                                    <input class="settings-input" id="telephone" type="date"
                                           name="telephone" pattern="[0-9+\- ]+" value="${person.telephone}">
                                </div>
                            </div>
                            <div class="settings-line">
                                <label class="settings-label" for="password">${password}</label>
                                <div class="settings-input-wrap">
                                    <input id="password" name="password" class="settings-input">
                                </div>
                            </div>
                            <div class="settings-line">
                                <label class="settings-label" for="info">${info}</label>
                                <div class="settings-input-wrap">
                                    <textarea class="settings-info" id="info" name="info" maxlength="255"></textarea>
                                </div>
                            </div>
                            <div>
                                <button class="setting-button" type="submit">${signUp}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
