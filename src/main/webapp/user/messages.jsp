<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="person" scope="session" type="models.Person"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Messages</title>
    <link rel="stylesheet" href="/css/styles.css">
    <script src="/js/websocket.js"></script>
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

                    <p>Chat room</p>
                    <br/>
                    <div id="addMsg">
                        <div class="button"><a href="#" OnClick="showForm()">Add a msg</a></div>
                        <form id="msgForm">
                            <input type="hidden" name="userId" value="${person.id}">
                            <textarea name="text" id="text" title="123123"></textarea>
                            <button class="msg-button" value="Send" onclick=startFormSubmit()>submit</button>
                            <input type="reset" class="button" value="Cancel" onclick=hideForm()>

                        </form>
                    </div>
                    <br/>

                    <div id="content">
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
