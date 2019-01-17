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
        <title><fmt:message key="title.page.activeProposal" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>
        <c:set var="lastPage" value="/jsp/proposal/active_proposal.jsp" scope="session"/> 
        <div class="container">
            <br>
            <br>
            <br>
            <h4 class="text-center"><fmt:message key="activeProposal.table.name" bundle="${rb}"/></h4>
            <p style="color:#e91e63">${invalidState}</p><p style="color:#e91e63">${failureMessage}</p>                         
            <br>
            <br> 
            <c:if test="${not empty proposal}">
                <div class="header" id="menu">
                    <nav class="navbar navbar-nav">
                        <ul class="navbar-nav navbar-btn btn-default">
                            <li>
                                <form name="find_evaluation_by_user_assigned" method="POST"  action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="find_evaluation_by_user_assigned"/>
                                    <input type="hidden" name="tender_id" value="${proposal.tender.id}"/>
                                    <input type="hidden" name="proposal_id" value="${proposal.id}"/>
                                    <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="proposal.label.findEvaluationByUser" bundle="${rb}"/></button>
                                </form>
                            </li>
                            <li>
                                <form name="add_proposal_evaluation_by_user" method="POST"  action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="add_proposal_evaluation_by_user"/>
                                    <input type="hidden" name="tender_id" value="${proposal.tender.id}"/>
                                    <input type="hidden" name="proposal_id" value="${proposal.id}"/>
                                    <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="proposal.label.addEvaluationByUser" bundle="${rb}"/></button>
                                </form>
                            </li> 
                            <li><form name="all_proposal_evaluations" method="POST" action="${request.getContextPath}/procurement/controller">
                                    <input type="hidden" name="command" value="find_all_proposal_evaluations"/>    
                                    <input type="hidden" name="tender_id" value="${proposal.tender.id}"/>
                                    <input type="hidden" name="proposal_id" value="${proposal.id}"/>   
                                    <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.evaluation.pues'/></button>                                
                                </form>
                            </li>  
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
        <br>
        <br>
        <br>
         <c:import url="/jsp/proposal/proposal.jsp"/>
         <br>
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>