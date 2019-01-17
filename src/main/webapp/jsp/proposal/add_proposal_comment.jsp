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
        <c:set var="lastPage" value="/jsp/proposal/add_proposal_comment.jsp" scope="session"/>
        <div class="container">
            <br> 
            <br> 
            <br>
            <br>
            <br> 
            <form name="formAddProposal" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="add_proposal_comment" />
                <input type="hidden" name="tender_id" value="${tender.id}" />
                <br>
                <div class="form-group">
                    <label for="application"><fmt:message key="proposal.application" bundle="${rb}"/></label>                     

                    <textarea class="form-control" rows="7" id="application" name="application" maxlength="500" placeholder="<fmt:message key="proposal.application.placeholder" bundle="${rb}"/>" required><c:out value="${application}"/></textarea>
                </div>
                <button type="submit" name="submit" class="btn btn-success"><fmt:message key="proposal.form.submit" bundle="${rb}"/></button>
            </form>            
        </div>
        <br> 
        <c:import url="/jsp/tender/tender.jsp"/>
        <br>
        <br> 
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>