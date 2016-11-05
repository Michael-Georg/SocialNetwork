<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>upload</title>
</head>
<body>
<form action="Upload" method="post" enctype="multipart/form-data">
    <%--<input type="text" name="description"/>--%>
    <input type="file" name="file"/>
    <input type="submit"/>
</form>
</body>
</html>
