<%-- 
    Document   : confirmLogout
    Created on : May 28, 2024, 12:37:10 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/LibraryManage/CSS/loginstyle.css" />
        <title>Log Out</title>
    </head>
    <body>
        <div class="outloginbox">
            <center>
                <h2>Are you sure you want to log out?</h2>
                <a href="/LibraryManage/logout_session" class="buttonlink">YES</a>
                <a href="<%= request.getContextPath() %>/view_patron_info/index.jsp" class="buttonlink">NO</a>
            </center>
        </div>
    </body>
</html>
