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
        <title><fmt:message key="title.page.listUserProposal" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>
        <c:set var="lastPage" value="/jsp/proposal/list_user_proposal.jsp" scope="session"/> 
        <div class="container">
            <br>
            <br>
            <br>
            <h4 class="text-center"><fmt:message key="listUserProposal.table.name" bundle="${rb}"/></h4>
            <br>
            <p style="color:blueviolet">${successMessage}</p>
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
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty userProposals}">
                    <h4 class="text-center">
                        <fmt:message key="message.noProposals" bundle="${rb}"/>
                    </h4>
                </c:if>
                <c:forEach items="${userProposals}" var="proposal">
                    <tr>
                        <td><c:out value="${proposal.id}"/></td>
                        <td style="word-wrap: break-word; max-width:150px;"><c:out value="${proposal.tender.id.toString().concat(', ').concat(proposal.tender.name)}"/></td>
                        <td><c:out value="${proposal.appliedAt}"/></td>                  
                        <td style="word-wrap: break-word; max-width:150px;"><c:out value="${proposal.application}"/></td>     
                        <td>
                            <form name="download_file" method="POST"  action="${request.getContextPath}/procurement/download"  accept-charset="UTF-8">
                                <input type="hidden" name="fileToDownload" value="<c:out value="${proposal.filePath}"/>"/>
                                <button type="submit" style="width:250px;text-align:right; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="btn btn-default navbar-btn"><c:out value="${proposal.filePath}"/></button>
                            </form>
                        </td>
                        <td><c:out value="${proposal.user.login}"/></td>
                        <td><c:out value="${proposal.user.company.name.concat(', ').concat(proposal.user.company.country)}"/></td>
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