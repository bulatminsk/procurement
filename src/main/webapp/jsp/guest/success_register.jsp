<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
    <title><fmt:message key="title.page.successRegistration" bundle="${rb}"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <c:set var="language" value="${sessionScope.language}"/>
    <fmt:setLocale value="${sessionScope.locale}" /> 
    <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
</head>
<body>
    <c:set var="lastPage" value="/jsp/guest/success_register.jsp" scope="session" />
    <c:import url="/jsp/structure/header.jsp"/>
    <div class="container">
        <h3 class="text-center"><fmt:message key="message.congratulations" bundle="${rb}"/></h3>
        <hr/>
        <p class="text-center"><fmt:message key="message.success.registration" bundle="${rb}"/></p>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>