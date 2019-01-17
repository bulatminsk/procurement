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
    <title><fmt:message key="title.page.tenderScores" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/evaluation/tender_scores.jsp" scope="session"/>  
    <div class="container">
        <br> 
        <br> 
        <br> 
        <br>
        <h4 class="text-center"><fmt:message key="tenderScores.table.name" bundle="${rb}"/></h4>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th><fmt:message key="tenderScores.company" bundle="${rb}"/></th>
                    <th><fmt:message key="tenderScores.score" bundle="${rb}"/></th>
                    <th colspan="2"><fmt:message key="proposal.action" bundle="${rb}"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${final_scores}" var="position">
                    <tr>
                        <td><c:out value="${position.key.user.company.name.concat(', ').concat(position.key.user.company.country)}"/></td>
                        <td><c:out value="${position.value}"/></td>
                        <td><form id="all_proposal_evaluations" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="find_all_proposal_evaluations"/>    
                                <input type="hidden" name="tender_id" value="${position.key.tender.id}"/>
                                <input type="hidden" name="proposal_id" value="${position.key.id}"/>   
                                <button form="all_proposal_evaluations" type="submit"  style="width:120px" class="btn btn-default navbar-btn"><fmt:message key='label.evaluation.pues'/></button>                                
                            </form>
                        </td>    
                        <c:if test="${tender.winner!=null&&tender.winner.id==position.key.user.company.id}">
                            <td><input type="radio" value="${position.key.user.company.id}" name="winner_choise" checked></td>
                            </c:if>
                            <c:if test="${position.key.tender.winner==null}">
                            <td><input type="radio" value="${position.key.user.company.id}" name="winner_choise" form="choose_winner"></td>
                            </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form method="POST" action="${request.getContextPath}/procurement/controller" id="choose_winner">
            <input type="hidden" name="tender_id" value="${tender.id}"/> 
            <input type="hidden" value="choose_winner" name="command" /> 
            <c:if test="${not empty tender.winner}">
                <button form="choose_winner" type="submit"  style="width:120px" class="btn btn-default navbar-btn"><fmt:message key='label.evaluation.submit'/></button>    
            </c:if>
            <c:if test="${empty tender.winner and not empty final_scores}">
                <button form="choose_winner" type="submit"  style="width:120px" class="btn btn-default navbar-btn"><fmt:message key='label.evaluation.submit'/></button>     
            </c:if>
        </form>
        <br>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>         
</body>
</html>