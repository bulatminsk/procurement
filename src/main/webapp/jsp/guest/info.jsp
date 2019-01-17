<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

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
    <title><fmt:message key="title.page.info" bundle="${rb}"/></title>
</head>
<body class="w3-green">
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/guest/info.jsp" scope="session" />
    <div class="container">
        <div class="row">   
            <div class="col-lg-8">
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <h1><fmt:message key="message.info.welcome" bundle="${rb}"/></h1>
                <p><fmt:message key="message.info.description" bundle="${rb}"/></p>
                <blockquote>
                    <p> <fmt:message key="message.info.quote" bundle="${rb}"/></p>
                    <footer><fmt:message key="message.info.author" bundle="${rb}"/></footer>
                </blockquote>
            </div>
        </div>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>