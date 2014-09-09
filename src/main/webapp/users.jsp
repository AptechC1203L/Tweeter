<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : users
    Created on : Sep 9, 2014, 11:47:14 AM
    Author     : chin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
    Create new user:
    
    <form action="" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="" />
        <br/>
        <label for="fullname">Full Name:</label>
        <input type="text" id="fullname" name="fullname" value="" />
        <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="" />
        <br/>
        <label for="password">Confirm Password:</label>
        <input type="password" id="confirmed_password" name="confirmed_password" value="" />
        <br/>
        <input type="submit" value="Create" />
    </form>

    <c:forEach var="user" items="${requestScope.users}">
        ${user.getUserId()}<br/>
    </c:forEach>
    </body>
</html>
