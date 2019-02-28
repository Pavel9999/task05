<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />

    <fmt:message bundle="${loc}" key="local.singup"
                 var="singup_text" />
    <fmt:message bundle="${loc}" key="local.cancel"
                 var="cancel_text" />
    <fmt:message bundle="${loc}" key="local.first_name"
                 var="first_name_text" />
    <fmt:message bundle="${loc}" key="local.second_name"
                 var="second_name_text" />
    <fmt:message bundle="${loc}" key="local.last_name"
                 var="last_name_text" />
    <fmt:message bundle="${loc}" key="local.login"
                 var="login_text" />
    <fmt:message bundle="${loc}" key="local.password"
                 var="password_text" />
    <fmt:message bundle="${loc}" key="local.email"
                 var="email_text" />


</head>
<body>

<p>${message_text}</p>

<form action="/finish_singup" method="POST">
    <table style="with: 50%">
        <tr>
            <td>${first_name_text}</td>
            <td><input type="text" name="first_name" value="${first_name_fill}"  /></td>
        </tr>
        <tr>
            <td>${second_name_text}</td>
            <td><input type="text" name="second_name" value="${second_name_fill}"/></td>
        </tr>
        <tr>
            <td>${last_name_text}</td>
            <td><input type="text" name="last_name" value="${last_name_fill}"/></td>
        </tr>
        <tr>
            <td>${email_text}</td>
            <td><input type="text" name="email" value="${email_name_fill}"/></td>
        </tr>
        <tr>
            <td>${password_text}</td>
            <td><input type="password" name="password" value="${password_fill}"/></td>
        </tr>
        </table>
    <input type="submit" value="${singup_text}" />
</form>


<form action="${pageContext.request.contextPath}/" method="GET">
    <input type="submit" value="${cancel_text}" />
</form>



</body>
</html>