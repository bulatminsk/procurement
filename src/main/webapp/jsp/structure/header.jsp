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
        <div class="header" id="myHeader">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/guest/landing.jsp">
                            <fmt:message key='label.project'/>
                        </a>
                    </div>
                    <ul class="nav navbar-nav navbar-left">
                        <c:if test="${empty user}">
                            <a href="${pageContext.request.contextPath}/jsp/guest/login.jsp">
                                <button input type="submit" class="btn btn-danger navbar-btn"><fmt:message key='label.login'/></button>
                            </a>
                        </c:if>
                        <c:if test="${not empty user}">
                            <form name="LogOutForm" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="logout" />
                                <button input type="submit" class="btn btn-danger navbar-btn"><fmt:message key='label.log.out'/></button>
                            </form>
                        </c:if>
                        <c:if test="${not empty user}">
                            <form name="ProfileForm" method="POST" action="${request.getContextPath}/procurement/controller">
                                <input type="hidden" name="command" value="show_profile" />
                                <button input type="submit" class="btn btn-danger navbar-btn"><fmt:message key='label.profile'/></button>
                            </form>
                        </c:if>
                        <c:if test="${empty user}">
                            <li> <a href="${pageContext.request.contextPath}/jsp/guest/registration.jsp">
                                    <button type="submit" class="btn btn-danger navbar-btn"><fmt:message key='label.register'/></button>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                    <ul class="nav navbar-nav navbar-brand">
                        <li> <a href="${pageContext.request.contextPath}/jsp/guest/info.jsp">
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key='label.about.project'/></button>
                            </a>
                        </li>
                        <li> <a href="${pageContext.request.contextPath}/jsp/guest/contacts.jsp">
                                <button type="submit" class="btn btn-default navbar-btn"><fmt:message key='label.contacts'/></button>
                            </a>
                        </li>
                        <li> 
                            <c:if test="${not empty user.login}">
                                <a href="${pageContext.request.contextPath}/jsp/user/main.jsp">
                                    <button type="submit"  class="btn btn-default navbar-btn"><fmt:message key='label.main'/></button>
                                </a>
                            </c:if>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <div class="dropdown btn-lg">
                                <button type="button" class="btn btn-danger  dropdown-toggle " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ${sessionScope.locale} 
                                </button>
                                <ul class="dropdown-menu dropdown" >
                                    <li>
                                        <form name="English" method="POST" action="${request.getContextPath}/procurement/controller">
                                            <input type="hidden" name="command" value="locale"/>
                                            <button  class="btn" type="submit" name="language" value="EN" ><fmt:message key='label.enLanguage'/></button>
                                        </form>
                                    </li>
                                    <li>
                                        <form name="Russian" method="POST" action="${request.getContextPath}/procurement/controller">
                                            <input type="hidden" name="command" value="locale"/>
                                            <button class="btn "  type="submit"  name="language" value="RU" ><fmt:message key='label.ruLanguage'/></button>
                                        </form>

                                    </li>
                                </ul>
                            </div>                              
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <c:if test="${not empty user.login}">
            <c:import url="/jsp/structure/menu_tender.jsp"/>
            <c:import url="/jsp/structure/menu_proposal.jsp"/>
        </c:if>
    </body>
</html>