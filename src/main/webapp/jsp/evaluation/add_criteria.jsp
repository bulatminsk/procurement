<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
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
        <title><fmt:message key="title.page.addCriteria" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>        
        <c:set var="lastPage" value="/jsp/evaluation/add_criteria.jsp" scope="session"/>
        <div class="container">
            <br> 
            <br> 
            <br> 
            <br>
            <h4 class="text-center"><fmt:message key="addCriteria.table.name" bundle="${rb}"/></h4>          
            <br>
            <br>
            <fmt:message key="message.criteria.rules" bundle="${rb}"/>
            <table class="table table-bordered">
                <tr>
                    <th><fmt:message key="criteria.name" bundle="${rb}"/></th>
                    <th><fmt:message key="criteria.weight" bundle="${rb}"/> </th>  
                    <th><fmt:message key="criteria.action" bundle="${rb}"/></th>
                </tr>
                <c:if test="${empty criteria}">
                    <h4 class="text-center">
                        <fmt:message key="message.noCriteria" bundle="${rb}"/>
                    </h4>
                </c:if>
                <c:forEach items="${criteria}" var="position">
                    <tr>
                        <td><c:out value="${position.criteria}" /></td>                  
                        <td><c:out value="${position.weight}" /></td>
                        <td> <form name="deleteCriteria"  method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="delete_criteria">
                                <input type="hidden" name="criteria_to_delete_id" value="${position.id}">
                                <input type="hidden" name="tender_id" value="${tender.id}">
                                <button type="submit" class="btn btn-danger"><fmt:message key="delete.criteria" bundle="${rb}"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <p style="color:#e91e63">${failureMessage}</p>
                <form name="formAddEvaluation" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="add_evaluation" /> 
                    <tr>
                        <td>
                            <input type="text" name="criteria" autofocus  maxlength="80" required class="form-control" placeholder="<fmt:message key="criteria.name.placeholder" bundle="${rb}"/>" />
                        </td>
                        <td>                
                            <input type="number" name="weight"  min="1" max="100" required class="form-control" placeholder="<fmt:message key="criteria.weight.placeholder" bundle="${rb}"/>" />
                        </td>
                    <input type="hidden" name="tender_id" value="<c:out value="${tender.id}"/>"/>
                    </tr>
            </table>
            <button type="submit" name="submit" class="btn btn-success"><fmt:message key="criteria.form.submit" bundle="${rb}"/></button>
        </form>
    </div>
    <br>
    <c:import url="/jsp/tender/tender.jsp"/>    
    <br>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>