<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <style>
        <%@include file="css/cars.css"%>
    </style>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />

    <fmt:message bundle="${loc}" key="local.greeting"
                 var="greeting_text" />

    <fmt:message bundle="${loc}" key="local.all_cars"
                 var="all_cars_text" />
    <fmt:message bundle="${loc}" key="local.free_cars"
                 var="free_cars_text" />
    <fmt:message bundle="${loc}" key="local.rented_cars"
                 var="rented_cars_text" />

    <fmt:message bundle="${loc}" key="local.brand"
                 var="brand_text" />
    <fmt:message bundle="${loc}" key="local.model"
                 var="model_text" />
    <fmt:message bundle="${loc}" key="local.year"
                 var="year_text" />
    <fmt:message bundle="${loc}" key="local.mkp"
                 var="mkp_text" />
    <fmt:message bundle="${loc}" key="local.color"
                 var="color_text" />
    <fmt:message bundle="${loc}" key="local.horsepower"
                 var="horsepower_text" />
    <fmt:message bundle="${loc}" key="local.engine_size"
                 var="engine_size_text" />
    <fmt:message bundle="${loc}" key="local.miliage"
                 var="miliage_text" />
    <fmt:message bundle="${loc}" key="local.price"
                 var="price_text" />
    <fmt:message bundle="${loc}" key="local.rental"
                 var="rental_text" />
    <fmt:message bundle="${loc}" key="local.rent_avaible"
                 var="rent_avaible_text" />
    <fmt:message bundle="${loc}" key="local.already_in_rent"
                 var="already_in_rent_text" />
    <fmt:message bundle="${loc}" key="local.rent_car"
                 var="rent_car_text" />



</head>
<body>

<p>${message_text}</p>

<p>"${greeting_text}, ${last_name} ${first_name} ${second_name}"<p>



<form action="/cars" method="POST">
    <input type="hidden"  name="acc_type" value="client" />
    <input type="hidden"  name="req_type" value="all" />
    <input type="submit" value="${all_cars_text}" />
</form>

<form action="/cars" method="POST">
    <input type="hidden"  name="acc_type" value="client" />
    <input type="hidden"  name="req_type" value="free" />
    <input type="submit" value="${free_cars_text}" />
</form>

<form action="/cars" method="POST">
    <input type="hidden"  name="acc_type" value="client" />
    <input type="hidden"  name="req_type" value="rented" />
    <input type="submit" value="${rented_cars_text}" />
</form>



<table id="cars">
    <tr id="header">
        <td><p>${brand_text}</p></td>
        <td><p>${model_text}</p></td>
        <td><p>${year_text}</p></td>
        <td><p>${mkp_text}</p></td>
        <td><p>${color_text}</p></td>
        <td><p>${horsepower_text}</p></td>
        <td><p>${engine_size_text}</p></td>
        <td><p>${miliage_text}</p></td>
        <td><p>${price_text}</p></td>
        <td><p>${rental_text}</p></td>
    </tr>
    <c:forEach varStatus="i" var="car" items="${cars}" >
        <tr class="car">
            <td><p>${car.brand}</p></td>
            <td><p>${car.model}</p></td>
            <td><p>${car.year}</p></td>
            <td><p>${car.mkp}</p></td>
            <td><p>${car.color}</p></td>
            <td><p>${car.horsepower}</p></td>
            <td><p>${car.engine_size}</p></td>
            <td><p>${car.miliage}</p></td>
            <td><p>${car.price}</p></td>
            <td>
                <c:if test = "${car.rental == 'free'}">

                    <form action="/start_rent_client" method="POST">
                        <input type="hidden"  name="acc_type" value="client" />
                        <input type="hidden"  name="req_type" value="all" />
                        <input type="hidden"  name="car_id" value="${car.id}" />
                        <input type="submit" value="${rent_car_text}" />
                    </form>
                </c:if>

                <c:if test = "${car.rental != 'free'}">
                    <p>${already_in_rent_text}</p>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>