<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  
    <style>
        .center {
            display: block;
            margin-left: auto;
            margin-right: auto;
            width: 100%;
        }
    </style>
    <c:set var="language" value="${sessionScope.language}"/>
    <fmt:setLocale value="${sessionScope.locale}" /> 
    <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
    <title><fmt:message key="title.page.landing" bundle="${rb}"/></title>   
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/guest/landing.jsp" scope="session" />
    <div class="container">
        <div id="myCarousel" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="item active">
                    <img src="/procurement/images/Solid_Aqua_Graphite.png" alt="" style="width: 100%; height:auto; max-height: 400px" class="center">
                    <div class="carousel-caption">
                        <h1><fmt:message key="message.landing.1" bundle="${rb}"/></h1>
                        <br>
                        <h2><fmt:message key="message.landing.2" bundle="${rb}"/></h2>
                        <br>
                        <h2><fmt:message key="message.landing.3" bundle="${rb}"/></h2>          
                        <br>
                    </div>
                </div>
                <div class="item">
                    <img src="/procurement/images/Solid_Aqua_Graphite.png" alt="" style="width: 100%; height:auto; max-height: 400px" class="center">
                    <div class="carousel-caption">
                        <h2><fmt:message key="message.landing.11" bundle="${rb}"/></h2>
                        <h4><fmt:message key="message.landing.12" bundle="${rb}"/></h4>
                        <h4><fmt:message key="message.landing.13" bundle="${rb}"/></h4>   
                        <h4><fmt:message key="message.landing.14" bundle="${rb}"/></h4>
                        <h4><fmt:message key="message.landing.15" bundle="${rb}"/></h4>
                        <h4><fmt:message key="message.landing.16" bundle="${rb}"/></h4>
                        <h4><fmt:message key="message.landing.17" bundle="${rb}"/></h4>
                        <br>
                    </div>
                </div>
                <div class="item">
                    <img src="/procurement/images/Solid_Aqua_Graphite.png" alt="" style="width: 100%; height:auto; max-height: 400px" class="center">
                    <div class="carousel-caption">
                        <h2><fmt:message key="message.landing.21" bundle="${rb}"/></h2>
                        <h4><fmt:message key="message.landing.22" bundle="${rb}"/></h4>
                        <h4><fmt:message key="message.landing.23" bundle="${rb}"/></h4>
                        <h4><fmt:message key="message.landing.24" bundle="${rb}"/></h4>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                    </div>
                </div>
            </div>
            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>