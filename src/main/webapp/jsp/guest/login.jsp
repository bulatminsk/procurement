<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .center {
            display: block;
            margin-left: auto;
            margin-right: auto;
            width: 50%;
        }
    </style>
    <c:set var="language" value="${sessionScope.language}"/>
    <fmt:setLocale value="${sessionScope.locale}" /> 
    <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
    <title><fmt:message key="title.page.login" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/guest/login.jsp" scope="session"/>
    <div class="container">
        <div class="row">
            <div class=col-lg-4>
            </div>
            <div class=col-lg-8>
                <form name="LoginForm" method="POST" action="${request.getContextPath}/procurement/controller">
                    <input type="hidden" name="command" value="login" />
                    <br/><fmt:message key="label.login.login" bundle="${rb}"/><br/>
                    <input type="text" autofocus class="form-control" name="login" placeholder="Enter Login" value="" maxlength="20" required style="width: 30%"/>
                    <br/><fmt:message key="label.login.password" bundle="${rb}"/><br/>
                    <input type="password"  name="password" placeholder="Enter Password" value="" minlength="5" maxlength="20" required class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>   
                    <p style="color:#e91e63">${failureMessage}</p> 
                    <button type="submit" class="btn btn-success"><fmt:message key="label.login.login_button" bundle="${rb}"/></button>
                </form>
            </div>
        </div>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>