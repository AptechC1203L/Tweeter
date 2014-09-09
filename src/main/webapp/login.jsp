<%-- 
    Document   : login
    Created on : Sep 7, 2014, 9:11:13 PM
    Author     : chin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<h:master title="Tweeter Login">
    <form method="POST" action="j_security_check">
        <label>Username</label>
        <input type="text" name="j_username"> <br/>
        <label>Password</label>
        <input type="password" name="j_password"> <br/>

        <input type="submit" value="Login" />
    </form>
</h:master>