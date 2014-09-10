<%-- 
    Document   : postBox
    Created on : Sep 11, 2014, 2:47:21 AM
    Author     : chin
--%>

<%@tag description="A box that user uses to create a new post" pageEncoding="UTF-8"%>

<form action="${pageContext.servletContext.contextPath}/posts" method="POST" enctype="multipart/form-data">
    <input type="text" name="postContent" value="" />
    <input type="file" name="image" value="" />
    <input type="submit" value="Post" />
</form>