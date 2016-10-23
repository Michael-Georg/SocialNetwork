<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<jsp:useBean id="person" type="models.Person" scope="request"/>
<table>
    <tr>
        <td> ${person.firstName}</td>
    </tr>
    <tr>
        <td> ${person.lastName}</td>
    </tr>
</table>
</body>
</html>
