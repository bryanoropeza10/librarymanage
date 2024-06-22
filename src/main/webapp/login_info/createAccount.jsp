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
                <form action="/LibraryManage/patron_validate" method="get">
                <div>
                    <!-- Display error message if it exists -->
                    <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        ${errorMessage}
                    </div>
                    </c:if>
                    <p> Already have an Account? <a href="<%= request.getContextPath() %>/login_info/index.jsp" class="linkfile">I Have One</a> </p>
                    <label><input type="text" name="fname" placeholder="First Name" required="required" class="createField"/></label><!-- get patron first and last name -->
                    <label><input type="text" name="lname" placeholder="Last Name" required="required" class="createField" /></label>
                    <label><select name="gender" class="genderlist"placeholder="Gender"><option value="Male">Male</option><!-- get gender from user -->
                            <option value="Female">Female</option><option value="N/A" selected="selected">N/A</option></select>
                    <label><input type="text" name="address" placeholder="Address" required="required" class="createField" id="addressfield"/></label>
                    <label><input type="email" name="email" placeholder="Email" required="required" class="createField" id="emailfield"/></label>
                    <label><input type="tel" name="phone" placeholder="Cell Phone" required="required" class="createField" id="phonefield"/></label>
                    <label><input type="submit" value="Next" class="submitbox" /></label>
                </div>
                </form>
            </center>
        </div>
    </body>
</html>
