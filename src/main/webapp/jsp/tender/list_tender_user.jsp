<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <c:set var="language" value="${sessionScope.language}"/>
        <fmt:setLocale value="${sessionScope.locale}" /> 
        <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
        <title><fmt:message key="title.page.listTenderByUser" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>
        <c:set var="lastPage" value="/jsp/tender/list_tender_user.jsp" scope="session"/>   
        <div class="container">
            <br>
            <br>
            <br>
            <h4 class="text-center"><fmt:message key="tenderByUser.table.name" bundle="${rb}"/>, ${user.login}</h4>
            <p style="color:blueviolet">${successMessage} <br/></p>
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
                        <th colspan=3><fmt:message key="tender.action" bundle="${rb}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty userTenders}">
                    <h4 class="text-center">
                        <fmt:message key="message.noTenders" bundle="${rb}"/>
                    </h4>
                </c:if>
                <c:forEach items="${userTenders}" var="tender">
                    <tr>
                        <td><c:out value="${tender.id}" /></td>
                        <td style="word-wrap: break-word; max-width:80px;"><c:out value="${tender.name}" /></td>                  
                        <td style="word-wrap: break-word; max-width:80px;"><c:out value="${tender.category}" /></td>
                        <td style="word-wrap: break-word; max-width:150px;"><c:out value="${tender.description}" /></td>
                        <td><c:out value="${tender.price}" /></td>
                        <td><c:out value="${tender.deadlineAt}" /></td>
                        <td><c:out value="${tender.publishedAt}" /></td>
                        <td><label><input type="checkbox" disabled="disabled" <c:if test="${tender.isArchived}">checked="checked"</c:if>></label></td>
                        <td><c:out value="${tender.user.getLogin()}" /></td>
                        <td><c:out value="${tender.user.getCompany().getName()}" /></td>
                        <td>
                            <form name="go_tender" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="go_tender"/>
                                <input type="hidden" name="tender_id" value="${tender.id}"/>
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.goTender" bundle="${rb}"/></button>
                            </form> 
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <br> 
        <br> 
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>