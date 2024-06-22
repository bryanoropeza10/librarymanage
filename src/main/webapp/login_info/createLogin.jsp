<%-- 
    Document   : createLogin
    Created on : May 30, 2024, 2:45:03 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/LibraryManage/CSS/loginstyle.css" >
        <title>Create log in info</title>
    </head>
    <body>
        <div class="outloginbox">
            <center> 
                <form action="/LibraryManage/patron_create" method="post">
                    <!-- Display error message if it exists -->
                    <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        ${errorMessage}
                    </div>
                    </c:if>
                    <a href=""
                    <h1>Create Log in </h1>
                    <label><input type="text" name="username" placeholder="Enter Username: " required="required" class="usernamebox"/></label>
                    <label><input type="text" name="password" placeholder="Enter Password: " required="required" class="passwordbox" /></label>
                    <label><input type="password" name="repassword" placeholder="Re-Enter Password" required="required" class="passwordbox" /></label>
                    <label><input type="submit" value="Submit" class="submitbox"/></label>
                </form>
            </center>
        </div>
    </body>
</html>
