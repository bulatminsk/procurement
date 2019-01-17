<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
        function initialize() {
            var myLatlng = new google.maps.LatLng(53.927370, 27.682457);
            var mapOptions = {
                zoom: 15,
                center: myLatlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            }
            var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

            //=====Initialise Default Marker
            var marker = new google.maps.Marker({
                position: myLatlng,
                map: map,
                title: 'marker'
            });

            //=====Initialise InfoWindow
            var infowindow = new google.maps.InfoWindow({
                content: "<B>Procurement solution</B>"
            });

            //=====Eventlistener for InfoWindow
            google.maps.event.addListener(marker, 'click', function () {
                infowindow.open(map, marker);
            });
        }

        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    <c:set var="language" value="${sessionScope.language}"/>
    <fmt:setLocale value="${sessionScope.locale}" /> 
    <fmt:setBundle basename="resources.messages" var="rb" scope="session"/>
    <title><fmt:message key="title.page.contacts" bundle="${rb}"/></title>
</head>
<body>
    <c:import url="/jsp/structure/header.jsp"/>
    <c:set var="lastPage" value="/jsp/guest/contacts.jsp" scope="session" />
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-lg-5 ml-auto d-flex align-items-center mt-4 mt-md-0">
                <div>
                    <h1><fmt:message key="contacts.title" bundle="${rb}"/></h1>
                    <p><fmt:message key="contacts.text" bundle="${rb}"/></p>
                    <p><fmt:message key="contacts.name" bundle="${rb}"/></p>
                    <p><fmt:message key="contacts.phone" bundle="${rb}"/></p>
                    <p><fmt:message key="contacts.address" bundle="${rb}"/></p>
                </div>
                <div id="map-canvas" style="height:300px; width:500px"></div>
            </div>
        </div>
    </div>
    <c:import url="/jsp/structure/footer.jsp"/>
</body>
</html>