<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
    <head>
        <c:set var="language" value="${sessionScope.language}"/>
        <fmt:setLocale value="${sessionScope.locale}" /> 
        <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
    </head>
    <body>
        <div class="header" id="menu">
            <nav class="navbar navbar-nav">
                <c:if test="${not empty user.login}">
                    <ul class="navbar-nav navbar-btn btn-default">
                        <li><form name="user_tenders" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="find_user_proposal"/>                               
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.userProposals'/></button>                                
                            </form>
                        </li>        
                        <li>
                            <form name="all_tenders" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="find_tenders_published_and_not_arch"/>                                
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.placeProposal'/></button>                                
                            </form>
                        </li> 
                        <li><form name="go_proposal" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="go_proposal"/>
                                <input type="hidden" name="proposal_id" value="${proposal.id}"/>           
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.activeProposal'/></button>                                
                            </form>
                        </li> 
                    </ul>
                </c:if>
                <c:if test="${empty user.login}">
                    <ul class="navbar-nav">
                        <li><a href="${request.getContextPath}/procurement/jsp/guest/info.jsp">                                   
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.proposals'/></button>
                            </a>
                        </li>
                        <li><a href="${request.getContextPath}/procurement/jsp/guest/landing_proposal.jsp">                                   
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key='label.userProposals'/></button>
                            </a>
                        </li>
                        <li><a href="${request.getContextPath}/procurement/jsp/guest/landing_proposal.jsp">                                   
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key='label.placeProposal'/></button>
                            </a>
                        </li>
                    </ul>
                </c:if>       
            </nav>
        </div>
    </body>
</html>