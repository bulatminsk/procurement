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
    <title><fmt:message key="title.page.pues" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/evaluation/proposal_pues.jsp" scope="session"/>
    <div class="container">
        <br> 
        <br> 
        <br> 
        <br>
        <h4 class="text-center"><fmt:message key="pues.table.name" bundle="${rb}"/></h4>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th><fmt:message key="pues.user" bundle="${rb}"/></th>
                    <th><fmt:message key="pues.criteria" bundle="${rb}"/></th>
                    <th><fmt:message key="pues.criteriaWeight" bundle="${rb}"/></th>
                    <th><fmt:message key="pues.criteriaScore" bundle="${rb}"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pues}" var="position">
                    <c:forEach items="${position.value}" var="evaluation">
                        <c:if test="${position.key.login.hashCode()%2 == 0}">
                            <tr class="success">
                            </c:if>
                            <c:if test="${position.key.login.hashCode()%2 != 0}" >
                            <tr class="info">
                            </c:if>
                            <td><c:out value="${position.key.login}"/></td>
                            <td><c:out value="${evaluation.key.criteria}"/></td>
                            <td><c:out value="${evaluation.key.weight}"/></td>
                            <td><c:out value="${evaluation.value}"/></td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <br>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>