<%-- 
    Document   : profileLogin
    Created on : Jun 10, 2024, 7:59:58 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Requests Page</title>
        <link rel="stylesheet" href="/LibraryManage/CSS/patron_menu.css" type="text/css" />
    </head>
    <body>
        <div class="headerbox">
            <a href="<%= request.getContextPath() %>/view_patron_info/index.jsp" class="linkprofile">Account Summary</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/requests.jsp" class="linkprofile">Requests</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/profile.jsp" class="linkprofile">Profile</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/confirmLogout.jsp" class="linkprofile">Log Out</a>
        </div>
        <div class="lowerbodybox">
            <div class="pageholderbox">
                <h1 id="changepasswordTitle">Change Password</h1>
                <h3 id="showusernametitle">USERNAME: ${sessionScope.profobj.username}</h3>
                <div class="alignbox">
                    <c:if test="${not empty oldPasswordMatchError}">
                        <div class="error-message">
                            ${oldPasswordMatchError}
                        </div>
                    </c:if>
                    <c:if test="${not empty confirmPasswordMessage}">
                        <div class="confirm-message">
                            ${confirmPasswordMessage}
                        </div>
                    </c:if>
                <form action="/LibraryManage/save_password" method="post">
                    <label class="passwordspan">
                        <span>Enter Old Password</span>
                        <input type="password" name="oldpassword" required="" >
                    </label>
                    <c:if test="${not empty newPasswordMatchError}">
                        <div class="error-message">
                            ${newPasswordMatchError}
                        </div>
                    </c:if>
                    <label class="passwordspan">
                        <span>New Password</span>
                        <input type="password" name="newpassword" required="">
                    </label>
                    <label class="passwordspan">
                        <span>Re-Enter Password</span>
                        <input type="password" name="repassword" required="">
                    </label>
                    <label id="submitpassword">
                        <input type="submit" value="Save" id="submitpasswordbox">
                    </label>
                </form>
                </div>
            </div>
        </div>
    </body>
</html>
