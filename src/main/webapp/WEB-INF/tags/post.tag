<%--
    Document   : post
    Created on : Sep 9, 2014, 6:20:06 PM
    Author     : chin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8" body-content="empty"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="post" type="com.ngochin.tweeter.model.Post" required="true"%>

<div class="post">
    <div class="username">
        <a href="${pageContext.servletContext.contextPath}/user/${post.getUsername()}">${post.getPoster().getFullName()}</a>
    </div>
        <div class="post-time-stamp">${applicationScope.prettyTime.format(post.getTimestamp())}</div>
    <div class="post-content">${post.getText()}</div>

    <c:forEach var="comment" items="${post.getComments()}">
        Comment from ${comment.getUser().getFullName()}: ${comment.getText()}<br/>
    </c:forEach>

    <form action="${pageContext.servletContext.contextPath}/comments" method="POST">
        <input type="text" name="text" value="" />
        <input type="submit" value="Comment" />
        <input type="hidden" name="postId" value="${post.getId()}"/>
    </form>
</div>