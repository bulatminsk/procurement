<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cnt" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

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
    <title><fmt:message key="title.page.addCompany" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/company/add_company.jsp" scope="session" />
    <div class="col-lg-8">
        <h4 class="text-center"><fmt:message key="company.form.name" bundle="${rb}"/></h4>
        <form id="add_company_form" method="POST" action="${request.getContextPath}/procurement/controller" accept-charset="UTF-8">
            <input type="hidden" name="command" value="add_company"/>
            <p style="color:#e91e63">${failureMessage}</p>
            <p style="color:#e91e63">${companyExists}</p>
            <br/><fmt:message key="label.company.name" bundle="${rb}"/>
            <input type="text" name="name" value="<c:out value="${company.name}"/>" required minlength="1" maxlength="30" class="form-control" placeholder="<fmt:message key="company.name.placeholder" bundle="${rb}"/>" title="<fmt:message key="company.name.title" bundle="${rb}"/>" />
            <br/><fmt:message key="label.company.taxNumber" bundle="${rb}"/>
            <input type="text" name="tax_number" value="<c:out value="${company.taxNumber}"/>" required minlength="1" maxlength="30" class="form-control" placeholder="<fmt:message key="company.taxNumber.placeholder" bundle="${rb}"/>" title="<fmt:message key="company.taxNumber.title" bundle="${rb}"/>" />
            <br/><fmt:message key="label.company.web" bundle="${rb}"/>
            <input type="text" name="web" value="<c:out value="${company.web}"/>" maxlength="30" class="form-control" placeholder="<fmt:message key="company.web.placeholder" bundle="${rb}"/>" title="<fmt:message key="company.web.title" bundle="${rb}"/>" />
            <br/><fmt:message key="label.company.country" bundle="${rb}"/>
            <cnt:countries name="country" type="${position.getDisplayCountry()}"/>
            <br/>
            <button type="submit" name="submit" value ="add_company_form" class="btn btn-success" ><fmt:message key="button.company.submit" bundle="${rb}"/></button>
            <br>
        </form>
        <br>
        <br>
        <br>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>