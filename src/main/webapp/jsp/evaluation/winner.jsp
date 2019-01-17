<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <c:set var="language" value="${sessionScope.language}"/>
    <fmt:setLocale value="${sessionScope.locale}" /> 
    <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
    <title><fmt:message key="title.page.winner" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/evaluation/winner.jsp" scope="session"/>  
    <div class="container">
        <br> 
        <br> 
        <br> 
        <br>
        <h4 class="text-center"><fmt:message key="winner.table.name" bundle="${rb}"/></h4>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th><fmt:message key="evaluation.winner" bundle="${rb}"/></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><c:out value="${company.name.concat(', ').concat(company.country)}"/></td>
                </tr>
            </tbody>
        </table>
        <c:import url="/jsp/tender/tender.jsp"/>
        <br>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>