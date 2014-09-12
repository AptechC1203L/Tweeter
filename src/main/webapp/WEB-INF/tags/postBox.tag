<%-- 
    Document   : postBox
    Created on : Sep 11, 2014, 2:47:21 AM
    Author     : chin
--%>

<%@tag description="A box that user uses to create a new post" pageEncoding="UTF-8"%>

<div class="panel panel-default">
    
    <div class="panel-body">
        <form role="form" action="${pageContext.servletContext.contextPath}/post/" method="POST">
            <div class="form-group">
            <textarea class="form-control" rows="3" name="postContent" placeholder="What are you thinking?"></textarea>
            </div>
            <input type="submit" class="btn btn-primary" value="Post" />
        </form>
    </div>
    
</div>
