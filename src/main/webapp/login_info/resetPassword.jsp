<%-- 
    Document   : resetPassword
    Created on : Jun 4, 2024, 4:58:58 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/LibraryManage/CSS/loginstyle.css" />
        <title>Reset Password </title>
    </head>
    <body>
        <div class="outloginbox">
            <center>
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        ${errroMessage}
                    </div>
                </c:if>
                <h1 class="headertext">Reset Password</h1>
                <form action="/LibraryManage/reset_password" method="post"><!-- get user reset password -->
                    <label><input type="text" placeholder="Enter Password" name="password" class="passwordbox" /></label>
                    <label><input type="text" placeholder="Re-Enter Password" name="repassword" class="passwordbox" /></label>
                    <label><input type="submit" value="Submit" class="submitbox" /></label>
                </form>
            </center>
        </div>
    </body>
</html>
