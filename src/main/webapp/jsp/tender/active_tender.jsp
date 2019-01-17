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
        <title><fmt:message key="title.page.activeTender" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>
        <c:set var="lastPage" value="/jsp/tender/active_tender.jsp" scope="session"/> 
        <br>
        <br>
        <br>
        <div class="container">
            <h4 class="text-center"><fmt:message key="activeTender.table.name" bundle="${rb}"/></h4>
            <p style="color:#e91e63">${invalidState}</p>
            <br>
            <br>
            <br> 
            <c:if test="${not empty tender}">
                <div class="header" id="menu">
                    <nav class="navbar navbar-nav">
                        <ul class="navbar-nav navbar-btn btn-default">
                            <li><form name="define_criteria" method="POST" action="${request.getContextPath}/procurement/controller">
                                    <input type="hidden" name="command" value="define_criteria"/> 
                                    <input type="hidden" name="tender_id" value="${tender.id}"/>
                                    <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.define.criteria'/></button>                                
                                </form></li>
                            <li><form name="add_tender_user" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="appoint_tender_committee"/>
                                    <input type="hidden" name="tender_id" value="${tender.id}"/>
                                    <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.tender.addUser" bundle="${rb}"/></button>
                                </form></li>
                            <li><form name="publish_tender" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="publish_tender"/>
                                    <input type="hidden" name="tender_id" value="${tender.id}"/>
                                    <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.tender.publishTender" bundle="${rb}"/></button>
                                </form></li> 
                            <li><form name="find_proposals_by_tender" method="POST" action="${request.getContextPath}/procurement/controller">
                                    <input type="hidden" name="command" value="find_proposals_by_tender"/>
                                    <input type="hidden" name="tender_id" value="${tender.id}"/>
                                    <button type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.tender.show_proposals" bundle="${rb}"/></button>
                                </form></li>
                            <li><form name="find_tender_final_score" method="POST" action="${request.getContextPath}/procurement/controller">
                                    <input type="hidden" name="command" value="find_tender_final_score"/>    
                                    <input type="hidden" name="tender_id" value="${tender.id}"/>  
                                    <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.tender.show_scores'/></button>                                
                                </form></li>
                            <li><form name="delete_tender" method="POST" action="${request.getContextPath}/procurement/controller">
                                    <input type="hidden" name="command" value="delete_tender"/>    
                                    <input type="hidden" name="tender_id" value="${tender.id}"/>  
                                    <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.tender.delete'/></button>                                
                                </form></li>
                            </c:if>
                    </ul>
                </nav>
            </div>
        </div>
        <br>
        <br>
        <c:import url="/jsp/tender/tender.jsp"/>
        <br>
        <c:import url="/jsp/structure/footer.jsp"/>
    </body>
</html>