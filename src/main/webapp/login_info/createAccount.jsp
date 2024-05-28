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
                <form action="/LibraryManage/patron_create" method="post">
                <div id="formDiv" class="initialDiv" >
                    <p> Already have an Account? <a href="<%= request.getContextPath() %>/login_info/index.jsp" class="linkfile">I Have One</a> </p>
                    <label><input type="text" name="fname" placeholder="First Name" required="required" class="createField"/></label><!-- get patron first and last name -->
                    <label><input type="text" name="lname" placeholder="Last Name" required="required" class="createField" /></label>
                    <label><input type="text" name="address" placeholder="Address" required="required" class="createField" id="addressfield"/></label>
                    <label><input type="email" name="email" placeholder="Email" required="required" class="createField" id="emailfield"/></label>
                    <label><input type="tel" name="phone" placeholder="Cell Phone" required="required" class="createField" id="phonefield"/></label>
                    <label><button onclick="showform()" class="submitbox">Next</button> </label>
                </div>
                <div id="finalDiv" class="hidden" >
                    <label><buton onclick="hideform()" class="backbox">Back</buton></label>
                    <h1>Create Log in </h1>
                    <label><input type="text" name="username" placeholder="Enter Username: " required="required" class="usernamebox"/></label>
                    <label><input type="text" name="password" placeholder="Enter Password: " required="required" class="passwordbox" /></label>
                    <label><input type="password" name="repassword" placeholder="Re-Enter Password" required="required" class="passwordbox" /></label>
                    <label><input type="submit" value="Submit" class="submitbox"/></label>
                </div>
                </form>
            </center>
        </div>
                    <script>
                        function showform(){
                            document.getElementById("formDiv").classList.add('hidden');
                            document.getElementById("finalDiv").classList.remove('hidden');
                        }
                        function hideform() {
                           document.getElementById("formDiv").classList.remove('hidden');
                           document.getElementById("finalDiv").classList.add('hidden');
                        }
                    </script>
    </body>
</html>
