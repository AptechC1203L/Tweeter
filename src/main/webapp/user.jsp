<%-- 
    Document   : user
    Created on : Sep 9, 2014, 1:26:04 PM
    Author     : chin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<h:master title="${user.getFullName()}'s Homepage">
    <h2>${user.getFullName()}'s Homepage</h2>
    <hr/>
    <c:if test="${user.getUserId() == authUser.getUserId()}">
        <h:postBox/>
    </c:if>

    <c:forEach var="post" items="${posts}">
        <h:post post="${post}"/>
    </c:forEach>
</h:master>