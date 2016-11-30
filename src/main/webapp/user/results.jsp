<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="result" scope="request" type="java.util.List<models.Person>"/>
<%@ taglib uri="http://SocialNetwork.com" prefix="list"%>
<%@ taglib prefix="pl" uri="http://SocialNetwork.com" %>
<html>
<head>

    <title>Results</title>
    <link rel="stylesheet" href="/css/styles.css">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="localization.message" var="loc"/>
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
                            LIST IS EMPTY
                        </c:when>
                        <c:otherwise>
                           <p1> ${requestScope.resultMsg}</p1>
                        <pl:list collection="result" buttonType="${requestScope.optionalButton}"
                                 buttonName="${requestScope.buttonName}"/>
                          </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
