<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.List"%>
<%@page import="com.ngochin.tweeter.model.Post"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<h:master title="Tweeter">
    <form action="posts" method="POST">
        <input type="text" name="postContent" value="" />
        <input type="submit" value="Post" />
    </form>

    <c:forEach var="post" items="${requestScope.posts}">
        <div class="post">
            <div class="username">
                <a href="user/${post.getUsername()}">${post.getPoster().getFullName()}</a>
            </div>
            <div class="post-time-stamp">${post.getTimestamp()}</div>
            <div class="post-content">${post.getText()}</div>

            <c:forEach var="comment" items="${post.getComments()}">
                Comment from ${comment.getUser().getFullName()}: ${comment.getText()}<br/>
            </c:forEach>

            <form action="comments" method="POST">
                <input type="text" name="text" value="" />
                <input type="submit" value="Comment" />
                <input type="hidden" name="postId" value="${post.getId()}"/>
            </form>
        </div>
    </c:forEach>
</h:master>