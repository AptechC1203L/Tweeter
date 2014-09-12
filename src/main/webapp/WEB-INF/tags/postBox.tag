<%-- 
    Document   : postBox
    Created on : Sep 11, 2014, 2:47:21 AM
    Author     : chin
--%>

<%@tag description="A box that user uses to create a new post" pageEncoding="UTF-8"%>

<form action="${pageContext.servletContext.contextPath}/post/" method="POST">
    <input type="text" name="postContent" value="" />
    <input type="submit" value="Post" />
</form>