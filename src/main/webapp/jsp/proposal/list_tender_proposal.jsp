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
        <c:set var="language" value="${sessionScope.language}"/>
        <fmt:setLocale value="${sessionScope.locale}" /> 
        <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
        <title><fmt:message key="title.page.listTenderProposal" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>
        <c:set var="lastPage" value="/jsp/proposal/list_tender_proposal.jsp" scope="session"/>  
        <div class="container">
            <br>
            <br>
            <br>
            <h4 class="text-center"><fmt:message key="listTenderProposal.table.name" bundle="${rb}"/></h4>
            <c:import url="/jsp/tender/tender.jsp"/>
            <br>
            <br>            
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th><fmt:message key="proposal.id" bundle="${rb}"/></th>
                        <th><fmt:message key="proposal.tender" bundle="${rb}"/></th>             
                        <th><fmt:message key="proposal.appliedAt" bundle="${rb}"/></th>                
                        <th><fmt:message key="proposal.application" bundle="${rb}"/></th>
                        <th><fmt:message key="proposal.filePath" bundle="${rb}"/> </th>
                        <th><fmt:message key="proposal.user" bundle="${rb}"/></th>
                        <th><fmt:message key="proposal.company" bundle="${rb}"/></th>
                        <th colspan=3><fmt:message key="proposal.action" bundle="${rb}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty tenderProposals}">
                    <h4 class="text-center">
                        <fmt:message key="message.noProposals" bundle="${rb}"/>
                    </h4>
                </c:if>
                <c:forEach items="${tenderProposals}" var="proposal">
                    <tr>
                        <td><c:out value="${proposal.id}"/></td>
                        <td><c:out value="${proposal.tender.id}"/></td>
                        <td><c:out value="${proposal.appliedAt}"/></td>                  
                        <td style="word-wrap: break-word; max-width:150px;"><c:out value="${proposal.application}"/></td>     
                        <td>
                            <form name="download_file" method="POST"  action="${request.getContextPath}/procurement/download"  accept-charset="UTF-8">
                                <input type="hidden" name="fileToDownload" value="<c:out value="${proposal.filePath}"/>"/>
                                <button type="submit" style="width:150px;text-align:right; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="btn btn-default navbar-btn"><c:out value="${proposal.filePath}"/></button>
                            </form>
                        </td>
                        <td><c:out value="${proposal.user.login}"/></td>
                        <td><c:out value="${proposal.user.company.name.concat(', ').concat(proposal.user.company.country)}"/></td>
                        <td>
                            <form name="add_proposal_evaluation_by_user" method="POST"  action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                                <input type="hidden" name="command" value="add_proposal_evaluation_by_user"/>
                                <input type="hidden" name="tender_id" value="${proposal.tender.id}"/>
                                <input type="hidden" name="proposal_id" value="${proposal.id}"/>
                                <button type="submit" style="width:120px" class="btn btn-default navbar-btn"><fmt:message key="proposal.label.addEvaluationByUser" bundle="${rb}"/></button>
                            </form>
                        </td> 
                        <td><form name="all_proposal_evaluations" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="find_all_proposal_evaluations"/>    
                                <input type="hidden" name="tender_id" value="${proposal.tender.id}"/>
                                <input type="hidden" name="proposal_id" value="${proposal.id}"/>   
                                <button type="submit"  style="width:120px" class="btn btn-default navbar-btn"><fmt:message key='label.evaluation.pues'/></button>                                
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