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
        <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" media="screen"
              href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">
        <c:set var="language" value="${sessionScope.language}"/>
        <fmt:setLocale value="${sessionScope.locale}" /> 
        <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
        <title><fmt:message key="title.page.addTender" bundle="${rb}"/></title>
    </head>
    <body>
        <c:import url="/jsp/structure/header.jsp"/>        
        <c:set var="lastPage" value="/jsp/tender/add_tender.jsp" scope="session"/>
        <div class="container">
            <br> 
            <br> 
            <br> 
            <br> 
            <form name="formAddTender" method="POST" action="${request.getContextPath}/procurement/controller"  accept-charset="UTF-8">
                <input type="hidden" name="command" value="add_tender" /> 
                <p style="color:#e91e63">${failureMessage}</p>
                <br>
                <fmt:message key="tender.name" bundle="${rb}"/>
                <br>                
                <input type="text" name="tender_name" value="<c:out value="${tender.name}"/>" autofocus maxlength="80" class="form-control" placeholder="<fmt:message key="addTender.name.placeholder" bundle="${rb}"/>" /> <br> 
                <fmt:message key="tender.category" bundle="${rb}"/>
                <br>                
                <input type="text" name="category" value="<c:out value="${tender.category}"/>" maxlength="20" class="form-control" placeholder="<fmt:message key="addTender.category.placeholder" bundle="${rb}"/>" /> <br> 
                <br>
                <div class="form-group">
                    <label for="description"><fmt:message key="tender.description" bundle="${rb}"/></label>
                    <textarea class="form-control" rows="7" id="description" name="description" maxlength="500" placeholder="<fmt:message key="addTender.description.placeholder" bundle="${rb}"/>"  ><c:out value="${tender.description}"/></textarea>
                </div>
                <br>
                <fmt:message key="tender.price" bundle="${rb}"/>
                <br>                
                <input type="number" name="price" value="<c:out value="${tender.price}"/>" min="1" max="1000000" required class="form-control" placeholder="<fmt:message key="addTender.price.placeholder" bundle="${rb}"/>" /> 
                <br> 
                <br>
                <fmt:message key="tender.deadlineAt" bundle="${rb}"/>:
                <br>
                <div id="datetimepicker" class="input-group date">
                    <input type="text" class="datepicker" data-date-format="mm/dd/yyyy" name="deadline_at" value="<c:out value="${tender.deadlineAt}"/>" required>
                    <span class="add-on">
                        <i data-date-icon="icon-calendar"></i>
                    </span>
                </div>
                <br>
                <button type="submit" name="submit" class="btn btn-success"><fmt:message key="addTender.form.submit" bundle="${rb}"/></button>
            </form>
        </div>
        <br> 
        <br> 
        <c:import url="/jsp/structure/footer.jsp"/>

        <script type="text/javascript"
                src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js">
        </script>
        <script type="text/javascript"
                src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.js">
        </script>
        <script type="text/javascript">
            $('#datetimepicker').datetimepicker({
                format: 'yyyy-MM-dd',
                language: 'EN'
            });
        </script>
    </body>
</html>