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
        <title><fmt:message key="title.page.addProposal" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>        
        <c:set var="lastPage" value="/jsp/proposal/add_proposal.jsp" scope="session"/>
        <div class="container">
            <br> 
            <br> 
            <br> 
            <br>          
            <br>
            <p style="color:#e91e63">${invalidState}</p><p style="color:#e91e63">${failureMessage}</p> 
            <a href="${pageContext.request.contextPath}/jsp/proposal/add_proposal_comment.jsp">
                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.proposal.addProposalComment'/></button>
            </a>
            <br>
            <fmt:message key="label.proposal.proposalComment" bundle="${rb}"/>
            <h5 style="color:blueviolet"><c:out value="${application}"/></h5>
            <br>
            <c:if test="${empty file_source}">
                <a href="${pageContext.request.contextPath}/jsp/proposal/add_proposal_file.jsp">
                    <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.proposal.addProposalFile'/></button>
                </a>
            </c:if>
            <c:if test="${not empty file_source}">
                <li>
                    <form name="delete_file" method="POST"  action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                        <input type="hidden" name="command" value="delete_file_proposal"/>
                        <input type="hidden" name="fileToDelete" value="<c:out value="${file_source}"/>" />
                        <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.proposal.deleteFile" bundle="${rb}"/></button>
                    </form>
                </li>
                <li>
                    <form name="download_file" method="POST"  action="${request.getContextPath}/procurement/download"  accept-charset="UTF-8">
                        <input type="hidden" name="fileToDownload" value="<c:out value="${file_source}"/>" />
                        <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.proposal.downloadFile" bundle="${rb}"/></button>
                    </form>
                </li>
            </c:if>
            <br>
            <fmt:message key="label.proposal.proposalFile" bundle="${rb}"/>
            <h5 style="color:blueviolet"><c:out value="${file_source}"/></h5>
            <br>
            <form name="formAddProposal" method="POST" action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                <input type="hidden" name="command" value="add_proposal" />
                <button type="submit" name="submit" class="btn btn-success"><fmt:message key="addProposal.form.submit" bundle="${rb}"/></button>
            </form>
        </div>
        <br> 
        <br> 
        <br>
        <c:import url="/jsp/tender/tender.jsp"/>
        <br>
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>