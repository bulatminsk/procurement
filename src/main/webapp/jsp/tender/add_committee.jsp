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
        <title><fmt:message key="title.page.addCommittee" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>        
        <c:set var="lastPage" value="/jsp/tender/add_committee.jsp" scope="session"/>
        <div class="container">
            <br> 
            <br> 
            <br> 
            <h4 class="text-center"><fmt:message key="addCommittee.table.name" bundle="${rb}"/></h4>
            <br> 
            <br>
            <br> 
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th><fmt:message key="addCommittee.user" bundle="${rb}"/></th>
                        <th><fmt:message key="addCommittee.action" bundle="${rb}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${tenderAppointedUsers}" var="member">
                        <tr>
                            <td><c:out value="${member.login}" /></td>
                            <td> <form action="${request.getContextPath}/procurement/controller" method="post">
                                    <input type="hidden" name="command" value="delete_from_committee">
                                    <input type="hidden" name="member_to_delete_login" value="${member.login}">
                                    <input type="hidden" name="tender_id" value="${tender.id}">
                                    <button type="submit" class="btn btn-danger"><fmt:message key="delete.from.committee" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${companyUsers}" var="employee">
                        <tr>
                            <td><c:out value="${employee.login}" /></td>
                            <td> <form action="${request.getContextPath}/procurement/controller" method="post">
                                    <input type="hidden" name="command" value="add_to_committee">
                                    <input type="hidden" name="member_to_add_login" value="${employee.login}">
                                    <input type="hidden" name="tender_id" value="${tender.id}">
                                    <button type="submit" class="btn btn-success"><fmt:message key="add.to.committee" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <br> 
        <c:import url="/jsp/tender/tender.jsp"/>
        <br> 
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>