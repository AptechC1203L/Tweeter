<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.List"%>
<%@page import="com.ngochin.tweeter.model.Post"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<h:master title="Tweeter">
    <form action="${pageContext.servletContext.contextPath}/posts" method="POST">
        <input type="text" name="postContent" value="" />
        <input type="submit" value="Post" />
    </form>

    <c:forEach var="post" items="${requestScope.posts}">
        <h:post post="${post}"/>
    </c:forEach>
</h:master>