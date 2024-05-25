<%-- 
    Document   : recoverLogin
    Created on : May 25, 2024, 12:02:24 AM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Recover Password</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/LibraryManage/CSS/loginstyle.css" />
    </head>
    <body>
        <div class="outloginbox">
            <center>
                <form action="Recover_login" method="post"><!-- recover password for login -->
                    <h1>Forgot Your Password?</h1>
                        <p id="recoverParagraph">No problem we will send you an email message with a code.<br />
                        Just enter the code into the prompt and reset your password.</p>
                    <input type="email" class="createField" id="recoveremail" placeholder="Email" required="required" />
                    <input type="submit" class="submitbox" value="Submit" />
                </form>
            </center>
        </div>
    </body>
</html>
