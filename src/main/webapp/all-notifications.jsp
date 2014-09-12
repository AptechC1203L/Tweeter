<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : notification
    Created on : Sep 12, 2014, 10:18:29 AM
    Author     : chin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="h" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<h:master title="Notifications">
    <c:set var="notis" value="${sessionScope.authUser.getNotifications()}"/>
    <c:choose>
        <c:when test="${notis.size() == 0}">
            You don't have any notification.
        </c:when>
        <c:otherwise>
            <c:forEach var="noti" items="${notis}">
                <div>
                    <a href="${noti.getId()}">${noti.getMessage()}</a>
                    ${noti.getCreationTime()}
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</h:master>