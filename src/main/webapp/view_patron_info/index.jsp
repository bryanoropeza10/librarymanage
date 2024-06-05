<%-- 
    Document   : index
    Created on : May 25, 2024, 12:08:58 AM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Patron Home window</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/LibraryManage/CSS/patron_menu.css" type="text/css" />
    </head>
    <body>
        <div class="headerbox">
            <a href="<%= request.getContextPath() %>/view_patron_info/index.jsp" id="accountlink" class="linkprofile">Account Summary</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/requests.jsp" id="requestlink" class="linkprofile">Requests</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/profile.jsp" id="profilelink" class="linkprofile">Profile</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/confirmLogout.jsp" id="logoutlink" class="linkprofile">Log Out</a>
        </div>
        <div class="lowerbodybox">
            <div class="pageholderbox">
                
            </div>
        </div>
    </body>
</html>
