<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h:master title="Tweeter Users">
    Create new user:

    <form action="#" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="" />
        <br/>
        <label for="fullname">Full Name:</label>
        <input type="text" id="fullname" name="fullname" value="" />
        <br/>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="" />
        <br/>
        <label for="password">Confirm Password:</label>
        <input type="password" id="confirmed_password" name="confirmed_password" value="" />
        <br/>

        <select multiple name="roles">
            <option>admin</option>
            <option>user</option>
        </select>
        <br/>
        <input type="submit" value="Create" />
    </form>

    <c:forEach var="user" items="${requestScope.users}">
        ${user.getUserId()}<br/>
    </c:forEach>
</h:master>
