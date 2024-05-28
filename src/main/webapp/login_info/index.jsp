<%-- 
    Document   : index
    Created on : May 24, 2024, 11:20:56 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Log In Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/LibraryManage/CSS/loginstyle.css" type="text/css" rel="stylesheet" /><!-- connected to css file -->
    </head>
    <body>
        <!-- get user username and password to validate in login_authen java file -->
        <!-- use css to create a log in box in the middle of the screen -->
        <div class="outloginbox">
            <div>
                <center> 
                    <p>Don't have an Account? <a href="<%= request.getContextPath() %>/login_info/createAccount.jsp" class="linkfile">Create Account </a> </p> 
                    <form action="<%= request.getContextPath() %>/login_authen" method="post" >
                    <!-- style username input  in css-->
                    <label><input placeholder="Username" type="text" name="username" class="usernamebox"/></label><!-- input username -->
                    <br />
                    <!-- stye password input in css -->
                    <label><input placeholder="Pasword" type="password" name="password" class="passwordbox"/> </label><!--<!-- input password -->
                    <br /> 
                    <label><input type="submit" value="Log In" class="submitbox"/></label><!-- Submit username and password -->
                    <p><a href="<%= request.getContextPath() %>/login_info/recoverLogin.jsp" class="linkfile" >Recover Password</a></p>
                    </form>
                </center>
            </div><!-- its for the inside box that handles the position -->
        </div>
        
    </body>
</html>

