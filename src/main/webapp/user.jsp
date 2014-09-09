<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : user
    Created on : Sep 9, 2014, 1:26:04 PM
    Author     : chin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${requestScope.user.getFullName()}'s Homepage</title>
    </head>
    <body>
        ${requestScope.user.getFullName()}'s Homepage <br/>
    </body>
</html>
