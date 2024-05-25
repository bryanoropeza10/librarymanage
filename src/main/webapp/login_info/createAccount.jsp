<%-- 
    Document   : createAccount
    Created on : May 24, 2024, 11:50:28 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Account</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/LibraryManage/CSS/loginstyle.css" >
    </head>
    <body>
        <div class="outloginbox">
            <center> 
                <p> Already have an Account? <a href="<%= request.getContextPath() %>/login_info/index.jsp" class="linkfile">I Have One</a> </p>
                <form action="/LibraryManage/patron_create" method="post">
                    <input type="text" name="fname" placeholder="First Name" required="required" class="createField"/><!-- get patron first and last name -->
                    <input type="text" name="lname" placeholder="Last Name" required="required" class="createField" />
                    <input type="text" name="address" placeholder="Address" required="required" class="createField" id="addressfield"/>
                    <input type="email" name="email" placeholder="Email" required="required" class="createField" id="emailfield"/>
                    <input type="tel" name="phone" placeholder="Cell Phone" required="required" class="createField" id="phonefield"/>
                    <input type="submit" value="Create Account" class="submitbox" />
                </form>
            </center>
        </div>
    </body>
</html>
