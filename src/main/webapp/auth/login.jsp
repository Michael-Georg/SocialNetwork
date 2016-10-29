<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
    <link rel="stylesheet" href="/css/styles.css" type="text/css"/>
</head>
<body>
<div id="wrapper">
    <form name="login" method="post" action="/Login">
        <input name="username" title="Login" placeholder="Email"/><br/>
        <input placeholder="password" type="password" name="password" autocomplete="off" title="Password"/><br/>
        <button type="submit">Sing In</button>
    </form>

    ${requestScope.get("errorMsg")}
</div>
</body>
</html>