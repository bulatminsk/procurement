<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <title>Access denied</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <c:set var="lastPage" value="/jsp/error/access_denied.jsp" scope="session"/>
    <c:import url="/jsp/structure/header.jsp"/>
    <br>
    <br>
    <br>
    <br>
    <br>
    <h3 class="text-center"><fmt:message key="access.denied" bundle="${rb}"/></h3>
    <br>   
    <div class="container">
        <div class="col-lg-8">
            <c:if test="${role == 'CLIENT'}">
                <a href="${pageContext.request.contextPath}/jsp/user/main.jsp">
                    <button type="submit" value="MainPage" class="btn btn-success"><fmt:message bundle="${rb}" key="label.main.page"/></button></a>
                </c:if>
                <c:if test="${role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/jsp/admin/admin_main.jsp">
                    <button type="submit" value="MainPage" class="btn btn-success"><fmt:message bundle="${rb}" key="label.main.page"/></button></a>
                </c:if>
                <c:if test="${empty role}">
                <a href="${pageContext.request.contextPath}/jsp/user/main.jsp">
                    <button type="submit" value="Login" class="btn btn-success"><fmt:message bundle="${rb}" key="label.main.page"/></button></a>
                </c:if>
        </div>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>