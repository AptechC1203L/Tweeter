<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="com.ngochin.tweeter.model.Post"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
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
        <form action="Logout">
            <input type="submit" value="Logout" />
        </form>

        <form action="Posts" method="POST">
            <input type="text" name="postContent" value="" />
            <input type="submit" value="Post" />
        </form>

        <c:forEach var="post" items="${requestScope.posts}">
            <div class="post">
                <div class="username">${post.getUsername()}</div>
                <div class="post-time-stamp">${post.getTimestamp()}</div>
                <div class="post-content">${post.getText()}</div>
                
                <c:forEach var="comment" items="${post.getComments()}">
                    Comment from ${comment.getUserId()}: ${comment.getText()}<br/>
                </c:forEach>

                <form action="Comments" method="POST">
                    <input type="text" name="text" value="" />
                    <input type="submit" value="Comment" />
                    <input type="hidden" name="postId" value="${post.getId()}"/>
                </form>
            </div>
        </c:forEach>
    </body>
</html>
