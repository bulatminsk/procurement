<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="SearchTag" prefix="st"%>
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
    <title><fmt:message key="title.page.profile" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/user/update_profile.jsp" scope="session" />
    <br>
    <br>
    <br>
    <div class="container">
        <h4 class="text-center"><fmt:message key="profile.form.name" bundle="${rb}"/>
            <c:out value="${user.login}"/></h4>
        <form id="go_company" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
            <input type="hidden" name="command" value="go_company"/>
        </form> 
        <fmt:message key="label.info.company" bundle="${rb}"/><button form="go_company" type="submit" class="btn btn-default navbar-btn"><fmt:message key="label.add.newCompany" bundle="${rb}"/></button>
        <st:searchCompany  field="${field}" search="${search}"/>
        <form  id="search_company"  method="POST" accept-charset="UTF-8">
            <input type="hidden" name="command" value="search_company"/>
            <fmt:message key="label.search.company" bundle="${rb}"/>
            <select  name="field" id ="inField">
                <option <c:if test="${field == 'taxNumber'}">selected="selected"</c:if> value="taxNumber" ><fmt:message key="label.search.taxNumber" bundle="${rb}"/></option>
                <option <c:if test="${field == 'name'}">selected="selected"</c:if> value="name" ><fmt:message key="label.search.name" bundle="${rb}"/></option>
                <option <c:if test="${field == 'country'}">selected="selected"</c:if> value="country" ><fmt:message key="label.search.country" bundle="${rb}"/></option>
                </select> 
            <fmt:message key="label.search.search" bundle="${rb}"/>
            <input class="form-control" name="search" type="search"/>
            <input form="search_company" name="submit" type="submit" value="submit"/>
        </form>
        <fmt:message key="label.add.company" bundle="${rb}"/>  
        <select name="company_id" form="update_profile" size="${companyList.size()+1}">
            <c:if test="${user.company.id!=0}">
                <option selected value="<c:out value="${user.company.id}"/>"><c:out value="${user.company.name}"/>  </option>
            </c:if>
            <c:if test="${not empty companyAdded and (empty user.company or user.company.id==0)}">
                <option selected value="<c:out value="${companyAdded.id}"/>"><c:out value="${companyAdded.name}"/></option>
            </c:if>
            <c:if test="${user.company.id==0 and empty companyAdded}">
                <option selected value=0><fmt:message key="label.choose.company" bundle="${rb}"/></option>
            </c:if>
            <c:if test="${not empty companyList}">
                <c:forEach items="${companyList}" var="company">
                    <option value="<c:out value="${company.id}"/>"><c:out value="${company.name.concat('/ ').concat(company.taxNumber).concat('/ ').concat(company.country)}"/></option>
                </c:forEach>
            </c:if>
        </select>
        <br>
        <br>
        <br>
        <form id="update_profile" action="${request.getContextPath}/procurement/controller" method="POST" accept-charset="UTF-8">
            <div class="col-lg-8">
                <input type="hidden" name="command" value="update_profile"/>
                <br>
                <p style="color:#e91e63">${failureMessage}</p>
                <p style="color:blueviolet">${profileUpdated} <br/></p>
                    <fmt:message key="label.change.firstName" bundle="${rb}"/>
                <input type="text" class="form-control" name="first_name" value="<c:out value="${user.firstName}"/>" maxlength="20"  placeholder="<fmt:message key="user.firstname.placeholder" bundle="${rb}"/>" title="<fmt:message key="user.firstname.title" bundle="${rb}"/>" ><br>
                <fmt:message key="label.change.lastName" bundle="${rb}"/>
                <input type="text" class="form-control" name="last_name" value="<c:out value="${user.lastName}"/>"  maxlength="20" placeholder="<fmt:message key="user.lastname.placeholder" bundle="${rb}"/>" title="<fmt:message key="user.lastname.title" bundle="${rb}"/>" ><br>
                <fmt:message key="label.change.password" bundle="${rb}"/>
                <input type="text"  class="form-control" name="password" value="" required minlength="5" maxlength="20" placeholder="<fmt:message key="user.password.placeholder" bundle="${rb}"/>" title="<fmt:message key="user.password.title" bundle="${rb}"/>" ><br>
                <fmt:message key="label.change.email" bundle="${rb}"/>
                <input type="text" class="form-control" name="email" value="<c:out value="${user.email}"/>" required minlength="5" maxlength="100" placeholder="<fmt:message key="user.email.placeholder" bundle="${rb}"/>" title="<fmt:message key="user.email.title" bundle="${rb}"/>" ><br>
                <br>
                <br>  
                <button form="update_profile" type="submit" name="update" value="Confirm" class="btn btn-success"><fmt:message key="button.change.submit" bundle="${rb}"/></button>
            </div>
        </form>
    </div>
    <br>
    <br>
    <br>
    <br>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>
