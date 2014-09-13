<%-- 
    Document   : master
    Created on : Sep 9, 2014, 7:18:29 PM
    Author     : chin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>

<c:set var="ctxPath" value="${pageContext.servletContext.contextPath}"></c:set>
<c:set var="user" value="${sessionScope.authUser}"/>

<%-- any content can be specified here e.g.: --%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>${title}</title>
        
        <link href="${ctxPath}/static/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctxPath}/static/css/tweeter.css" rel="stylesheet">
        
    </head>
    <body>
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${ctxPath}">Tweeter</a>
                </div>
                <c:if test="${not empty sessionScope.authUser}">
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${ctxPath}/user/${user.getUserId()}">${user.getFullName()}</a></li>
                        <li><a href="${ctxPath}/notification/">${user.getNotifications().size()} Unread Notification(s)</a></li>
                        <c:if test="${pageContext.request.isUserInRole('admin')}">
                            <li><a href="${ctxPath}/admin">Admin</a></li>
                        </c:if>
                        
                        <form action="${pageContext.servletContext.contextPath}/logout" class="navbar-form navbar-right">
                            <input type="submit" class="btn btn-default" value="Logout" />
                        </form>
                    </ul>
                </div><!--/.nav-collapse -->
                </c:if>
            </div>
        </div>
        
        <div class="container">
            <jsp:doBody></jsp:doBody>
        </div>

        <script src="${ctxPath}/static/js/jquery.min.js"></script>
        <script src="${ctxPath}/static/js/bootstrap.min.js"></script>
    </body>
</html>
