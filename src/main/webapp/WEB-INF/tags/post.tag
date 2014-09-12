<%--
    Document   : post
    Created on : Sep 9, 2014, 6:20:06 PM
    Author     : chin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8" body-content="empty"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="post" type="com.ngochin.tweeter.model.Post" required="true"%>
<c:set var="ctxPath" value="${pageContext.servletContext.contextPath}"/>

<div class="post panel panel-default">
    <div class="panel-heading">
        <a class="username" href="${ctxPath}/user/${post.getUsername()}">${post.getPoster().getFullName()}</a>
        <p>
            <a class="post-time-stamp" href="${ctxPath}/post/${post.getId()}">${applicationScope.prettyTime.format(post.getTimestamp())}</a>
        </p>
    </div>

    <div class="post-content panel-body">
        <c:forEach var="fragment" items="${post.getContentFragments()}">
            <c:choose>
                <c:when test="${fragment.getClass().getSimpleName() == 'String'}">
                    ${fragment}
                </c:when>
                <c:when test="${fragment.getClass().getSimpleName() == 'User'}">
                    <a href="${ctxPath}/user/${fragment.getUserId()}">@${fragment.getFullName()}</a>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </div>

    <div class="panel-footer">

        <ul class="list-group">
            <c:forEach var="comment" items="${post.getComments()}">
                <li class="list-group-item">
                    <a href="${ctxPath}/user/${comment.getUser().getUserId()}">${comment.getUser().getFullName()}</a>:
                    ${comment.getText()}
                </li>
            </c:forEach>
        </ul>


        <form role="form" action="${ctxPath}/comments" method="POST">
            <div class="form-group">
                <textarea name="text" rows="1"></textarea>
            </div>
            <input type="submit" class="btn btn-default btn-sm" value="Reply" />
            <input type="hidden" name="postId" value="${post.getId()}"/>
        </form>
    </div>
</div>