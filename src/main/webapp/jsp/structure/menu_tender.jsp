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
        <hr>
        <hr>
        <div class="header" id="menu">
            <nav class="navbar navbar-nav">
                <c:if test="${not empty user.login}">
                    <ul class="navbar-nav navbar-btn btn-default">
                        <li><form name="user_tenders" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="find_user_tenders"/>                               
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.userTenders'/></button>                                
                            </form>
                        </li>
                        <li><form name="tenders_by_tc_member" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="find_tenders_by_tc_member"/>                               
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.evaluateTenders'/></button>                                
                            </form>
                        </li>      
                        <li><form name="place_tender" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="place_tender"/>          
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.placeTender'/></button>                                
                            </form>
                        </li>      
                        <li><form name="go_tender" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="go_tender"/>
                                <input type="hidden" name="tender_id" value="${tender.id}"/>           
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.activeTender'/></button>                                
                            </form>
                        </li>      
                    </ul>
                </c:if>
                <c:if test="${empty user.login}">
                    <ul class="navbar-nav">
                        <li><a href="${request.getContextPath}/procurement/jsp/guest/info.jsp">                                   
                                <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.tenders'/></button>
                            </a>
                        </li>
                        <li><a href="${request.getContextPath}/procurement/jsp/guest/landing_tender.jsp">                                   
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key='label.userTenders'/></button>
                            </a>
                        </li>
                        <li><a href="${request.getContextPath}/procurement/jsp/guest/landing_tender.jsp">                                   
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key='label.placeTender'/></button>
                            </a>
                        </li>

                    </ul>
                </c:if>       
            </nav>
        </div>
    </body>
</html>