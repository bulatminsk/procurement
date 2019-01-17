<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <c:set var="language" value="${sessionScope.language}"/>
        <fmt:setLocale value="${sessionScope.locale}" /> 
        <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
        <title><fmt:message key="title.page.listTender" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>
        <c:set var="lastPage" value="/jsp/tender/list_tender.jsp" scope="session"/> 
        <br>
        <div class="container">
            <br>
            <br>
            <br>
            <h4 class="text-center"><fmt:message key="tender.table.name" bundle="${rb}"/></h4>
            <br>
            <p style="color:blueviolet"><fmt:message key="search.message" bundle="${rb}"/></p>  
            <input class="form-control" id="tendersInput" type="text" placeholder=<fmt:message key="search.placeholder" bundle="${rb}"/>
                   <br>
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
                        <th><fmt:message key="tender.user" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.company" bundle="${rb}"/></th>
                        <th><fmt:message key="tender.action" bundle="${rb}"/></th>
                    </tr>
                </thead>
                <tbody id="tableToFilter">
                    <c:if test="${empty tenders}">
                    <h4 class="text-center">
                        <fmt:message key="message.noTenders" bundle="${rb}"/>
                    </h4>
                </c:if>
                <c:forEach items="${tenders}" var="tender">
                    <tr>
                        <td><c:out value="${tender.id}" /></td>
                        <td style="word-wrap: break-word; max-width:80px;"><c:out value="${tender.name}" /></td>                  
                        <td style="word-wrap: break-word; max-width:80px;"><c:out value="${tender.category}" /></td>
                        <td style="word-wrap: break-word; max-width:150px;"><c:out value="${tender.description}" /></td>
                        <td><c:out value="${tender.price}" /></td>
                        <td><c:out value="${tender.deadlineAt}" /></td>
                        <td><c:out value="${tender.publishedAt}" /></td>
                        <td><c:out value="${tender.user.getLogin()}" /></td>
                        <td><c:out value="${tender.user.getCompany().getName()}" /></td>
                        <td>
                            <form name="apply_tender" method="POST"  action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                                <input type="hidden" name="command" value="apply_tender"/>
                                <input type="hidden" name="tender_id" value="${tender.id}"/>
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="tender.label.applyTender" bundle="${rb}"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <br> 
        <br> 
        <script>
            $(document).ready(function () {
                $("#tendersInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#tableToFilter tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script>
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>
