<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title><fmt:message key="title.page.evaluationUpdate" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/evaluation/proposal_evaluation_update.jsp" scope="session"/>  
    <div class="container">
        <br> 
        <br> 
        <br> 
        <br>
        <h4 class="text-center"><fmt:message key="evaluationUpdate.table.name" bundle="${rb}"/><c:out value="${user.login}"/></h4>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th><fmt:message key="evaluation.id" bundle="${rb}"/></th>
                    <th><fmt:message key="evaluation.criteriaName" bundle="${rb}"/></th>             
                    <th><fmt:message key="evaluation.criteriaWeight" bundle="${rb}"/></th> 
                    <th><fmt:message key="evaluation.maxScore" bundle="${rb}"/></th>
                    <th><fmt:message key="evaluation.score" bundle="${rb}"/></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty evaluations}">
                <h4 class="text-center">
                    <fmt:message key="message.noEvaluations" bundle="${rb}"/>
                </h4>
            </c:if>
            <form name="formUpdateEvaluation" method="POST" action="${request.getContextPath}/procurement/controller">
                <input type="hidden" name="command" value="update_proposal_evaluation_by_user" /> 
                <input type="hidden" name="tender_id" value=<c:out value="${tender.id}"   /> /> 
                <input type="hidden" name="proposal_id" value=<c:out value="${proposal.id}"/> /> 
                <c:forEach items="${evaluations}" var="evaluation">
                    <tr>                        
                        <td><c:out value="${evaluation.key.id}"/></td>
                        <td><c:out value="${evaluation.key.criteria}"/></td>
                        <td><c:out value="${evaluation.key.weight}"/></td> 
                        <td><c:out value="${evaluation.key.maxScore}"/></td> 
                        <td> 
                            <input type="number" name="score" value="<c:out value="${evaluation.value}"/>"  max="10" class="form-control" placeholder="<fmt:message key="evaluation.score.placeholder" bundle="${rb}"/>" title="<fmt:message key="evaluation.score.title" bundle="${rb}"/>" required/>
                            <input type="hidden" name="evaluation_id" value="<c:out value="${evaluation.key.id}"/>">
                        </td>
                    </tr>                   
                </c:forEach>
                <button type="submit" class="btn btn-success"><fmt:message key="button.evaluation.submit" bundle="${rb}"/></button>
            </form>
            </tbody>
        </table>
        <h4 class="text-center"><fmt:message key="evaluation.proposal" bundle="${rb}"/></h4>
        <c:import url="/jsp/proposal/proposal.jsp"/>
        <br>
        <h4 class="text-center"><fmt:message key="evaluation.tender" bundle="${rb}"/></h4>
        <c:import url="/jsp/tender/tender.jsp"/>
        <br>
        <br>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>