<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <title>Login</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />

    <fmt:message bundle="${loc}" key="local.login"
                 var="login_text" />
    <fmt:message bundle="${loc}" key="local.cancel"
                 var="cancel_text" />

    <fmt:message bundle="${loc}" key="local.email"
                 var="email_text" />
    <fmt:message bundle="${loc}" key="local.password"
                 var="password_text" />


</head>
<body>

<p>${message_text}</p>

<form action="/validation" method="POST">
    <table style="with: 50%">

        <tr>
            <td>${email_text}</td>
            <td><input type="text" name="email" /></td>
        </tr>
        <tr>
            <td>${password_text}</td>
            <td><input type="password" name="password" /></td>
        </tr></table>
    <input type="submit" value="${login_text}" />
</form>

<form action="${pageContext.request.contextPath}/" method="GET">
    <input type="submit" value="${cancel_text}" />
</form>

</body>

</html>
