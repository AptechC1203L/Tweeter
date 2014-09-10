<%-- 
    Document   : master
    Created on : Sep 9, 2014, 7:18:29 PM
    Author     : chin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>
<%@attribute name="user" type="com.ngochin.tweeter.model.User" %>

<%-- any content can be specified here e.g.: --%>
<html>
    <head>
        <title>${title}</title>
        <style>
        .post {
            margin-bottom: 20px;
        }
            
        .post .username {
            font-size: 150%;
            font-weight: bold;
            margin-bottom: 10px;
        }
            
        .post .post-time-stamp {
            font-size: 80%;
            margin-bottom: 10px;
        }
            
        .post .post-content {
            margin-bottom: 10px;
        }
        </style>
    </head>
    <body>
        <a href="${pageContext.servletContext.contextPath}">Home</a>
        
        <c:if test="${not empty user}">
            <a href="${pageContext.servletContext.contextPath}/user/${user.getUserId()}">${user.getFullName()}</a>
        
            <c:if test="${pageContext.request.isUserInRole('admin') == true}">
                <a href="${pageContext.servletContext.contextPath}/admin">Admin</a>
            </c:if>

            <form action="${pageContext.servletContext.contextPath}/logout">
                <input type="submit" value="Logout" />
            </form>
        </c:if>
        <jsp:doBody></jsp:doBody>
    </body>
</html>
