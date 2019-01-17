<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title><fmt:message key="title.page.registration" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/guest/registration.jsp" scope="session" />
    <div class="col-lg-8">
        <form name="RegistrationForm" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
            <input type="hidden" name="command" value="register"/>
            <p style="color:#e91e63">${failureMessage}</p>
            <br/><fmt:message key="label.registration.login" bundle="${rb}"/><br/>
            <input type="text" name="login" value="" minlength="5" maxlength="20" class="form-control" placeholder="<fmt:message key="user.login.placeholder" bundle="${rb}"/>" title="<fmt:message key="user.login.title" bundle="${rb}"/>" />
            <br/><fmt:message key="label.registration.password" bundle="${rb}"/><br/>
            <input type="password" name="password" value="" minlength="5" maxlength="20" class="form-control" placeholder="<fmt:message key="user.password.placeholder" bundle="${rb}"/>" title="<fmt:message key="user.password.title" bundle="${rb}"/>" />
            <br/>
            <button type="submit" name="submit" class="btn btn-success"><fmt:message key="button.registration.submit" bundle="${rb}"/></button>
            <br/>
        </form>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>