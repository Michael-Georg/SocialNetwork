<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="result" scope="request" type="java.util.List<models.Person>"/>
<%@ taglib uri="http://SocialNetwork.com" prefix="list" %>
<%@ taglib prefix="pl" uri="http://SocialNetwork.com" %>
<html>
<head>

    <title>Results</title>
    <link rel="stylesheet" href="/css/styles.css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
    <fmt:message bundle="${loc}" key="followers" var="followers"/>
    <fmt:message bundle="${loc}" key="blackList" var="blackList"/>
    <fmt:message bundle="${loc}" key="following" var="following"/>
    <fmt:message bundle="${loc}" key="resultMsg.empty" var="resultEmpty"/>
    <fmt:message bundle="${loc}" key="unfollow" var="unfollow"/>
    <fmt:message bundle="${loc}" key="block" var="block"/>
    <fmt:message bundle="${loc}" key="remove" var="remove"/>
</head>
<body>
<div id="wrapper">
    <jsp:include page="/WEB-INF/header.jsp"/>
    <div class="page_layout">
        <jsp:include page="/WEB-INF/sidebar.jsp"/>
        <div class="fl_r">
            <div class="page_body">
                <div class="page_block">
                    <c:choose>
                        <c:when test="${empty result}">
                            <div class="result-header">
                                    ${resultEmpty}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="result-header">
                                <h1><fmt:message bundle="${loc}" key="${requestScope.resultMsg}"/></h1>
                            </div>
                            <pl:list collection="result" buttonType="${requestScope.resultMsg}"
                                     buttonName="${pageScope[requestScope.buttonName]}"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>