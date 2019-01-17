<%@ tag body-content="scriptless"%>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<input class="form-control" type="text" width="10" id="countriesInput" list="countries" name="country" value="${company.country}"/>
<datalist id="countries">
    <c:forEach items="${countryList}" var="position">
        <option value="<c:out value="${position.getDisplayCountry()}"/>"><c:out value="${position.getDisplayCountry()}"/></option>
    </c:forEach>
</datalist><br>