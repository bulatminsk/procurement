<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>       
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th><fmt:message key="tender.id" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.name" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.category" bundle="${rb}"/></th>                
                        <th><fmt:message key="tender.description" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.price" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.deadlineAt" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.publishedAt" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.isArchived" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.user" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.company" bundle="${rb}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty tender}">
                    <h4 class="text-center">
                        <fmt:message key="message.noTender" bundle="${rb}"/>
                    </h4>
                </c:if>
                <tr>
                    <td><c:out value="${tender.id}" /></td>
                    <td style="word-wrap: break-word; max-width:80px;"><c:out value="${tender.name}" /></td>                  
                    <td style="word-wrap: break-word; max-width:80px;"><c:out value="${tender.category}" /></td>
                    <td style="word-wrap: break-word; max-width:150px;"><c:out value="${tender.description}" /></td>
                    <td><c:out value="${tender.price}" /></td>
                    <td><c:out value="${tender.deadlineAt}" /></td>
                    <td><c:out value="${tender.publishedAt}" /></td>
                    <td><label><input type="checkbox" disabled="disabled" <c:if test="${tender.isArchived}">checked="checked"</c:if>></label></td>
                    <td><c:out value="${tender.user.login}" /></td>
                    <td><c:out value="${tender.user.company.name.concat(', ').concat(tender.user.company.country)}" /></td>
                </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>