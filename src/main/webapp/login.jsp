<%-- 
    Document   : login
    Created on : Sep 7, 2014, 9:11:13 PM
    Author     : chin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<h:master title="Tweeter Login">
    <form role="form" method="POST" action="j_security_check">
        <legend>
            Login
        </legend>
        
        <p class="text-muted">Please enter your username and password.</p>
        
        <div class="form-group">
            <label for="j_username" class="form-label">Username</label>
            <input type="text" name="j_username" id="j_username" class="form-control">
        </div>
        
        <div class="form-group">
            <label for="j_password" class="form-label">Password</label>
            <input type="password" name="j_password" id="j_password" class="form-control">
        </div>

        <input type="submit" class="btn btn-default" value="Login" />
    </form>
</h:master>