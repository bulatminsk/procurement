<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
    <head>
        <c:set var="language" value="${sessionScope.language}"/>
        <fmt:setLocale value="${sessionScope.locale}" /> 
        <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
        <title>Error File Not Found</title>
    </head>
    <body>
        <c:set var="lastPage" value="/jsp/error/error_file.jsp" scope="session"/>  
        <p>Error! Sorry, requested file is not found</p>
        <a href="${pageContext.request.contextPath}/jsp/user/main.jsp">Go to main page</a>
        <br>
        <br>
        <br>

    </body>
</html>