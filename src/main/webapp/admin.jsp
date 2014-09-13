<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h:master title="Tweeter Users">
    <form action="#" role="form" method="POST">
        <legend>
            Create new user
        </legend>
        
        <c:if test="${not empty error}">
            <p class="alert alert-danger">${error}</p>
        </c:if>

        <div class="form-group">
            <label for="username" class="control-label">Username</label>
            <input type="text" id="username" class="form-control" name="username" value="" />
        </div>

        <div class="form-group">
            <label for="fullname" class="control-label">Full Name</label>
            <input type="text" id="fullname" class="form-control" name="fullname" value="" />
        </div>

        <div class="form-group">
            <label for="password" class="control-label">Password</label>
            <input type="password" id="password" class="form-control" name="password" value="" />
        </div>
        
        <div class="form-group">
            <label for="password" class="control-label">Confirm Password</label>
            <input type="password" id="confirmed_password" class="form-control" name="confirmed_password" value="" />
        </div>

        <select multiple name="roles" class="form-control" >
            <option>admin</option>
            <option selected>user</option>
        </select>
        <br/>
        <input type="submit" class="btn btn-default btn-lg" value="Create" />
    </form>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>#</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Roles</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach varStatus="st" var="user" items="${users}">
                <tr>
                    <td>${st.getIndex() + 1}</td>
                    <td>${user.getUserId()}</td>
                    <td>${user.getFullName()}</td>
                    <td>${user.getRoles()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</h:master>
