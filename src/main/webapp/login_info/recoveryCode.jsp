<%-- 
    Document   : recoveryCode
    Created on : May 31, 2024, 10:10:58 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/LibraryManage/CSS/loginstyle.css" />
        <title>JSP Page</title>
    </head>
    <body>
       <div class="outloginbox">
           <center>
               <h1 class="headertext">Enter Temp Security Code</h1>
               <form action="/LibraryManage/securitycode_validation" method="post">
                   <label><input type="number" class="numbersfield" id="firstNumber" min="0" max="9" maxlength="1" name="1number" oninput="movetoNext(this,'secondNumber')" required="required"/></label><!-- allowing only one number --> 
                   <label><input type="number" class="numbersfield" id="secondNumber" min="0" max="9" maxlength="1" name="2number" oninput="movetoNext(this,'thirdNumber'" required="required"/></label>
                   <label><input type="number" class="numbersfield" id="thirdNumber" min="0" max="9" maxlength="1" name="3number" oninput="movetoNext(this, 'fourthNumber'" required="required"/></label>
                   <label><input type="number" class="numbersfield" id="fourthNumber" min="0" max="9" maxlength="1" name="4number" oninput="movetoNext(this, 'fifthNumber'" required="required"/></label>
                   <label><input type="number" class="numbersfield" id="fifthNumber" min="0" max="9" maxlength="1" name="5number" oninput="movetoNext(this, 'sixthNumber'" required="required"/></label>
                   <label><input type="number" class="numbersfield" id="sixthNumber" min="0" max="9" maxlength="1" name="6number" oninput="movetoNext(this, null)" required="required"/></label>
                   <label><input type="submit" value="Submit" class="submitbox"></label>
                   <a href="/LibraryManage/securitycode_validation" class="linkfile" id="resendlink" name="resend">Resend Code</a> 
               </form>
           </center>
       </div>
        <script>
            function movetoNext(current, nextFieldID){
                if(current.value.length === 1 && nexFieldID){
                    document.getElementById(nextfield).focus();
                }
            }
        </script>
    </body>
</html>
