<%-- 
    Document   : profile
    Created on : May 29, 2024, 10:44:30 PM
    Author     : bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Profile Page</title>
    <link rel="stylesheet" href="/LibraryManage/CSS/patron_menu.css" type="text/css">
</head>
<body>
    <div class="headerbox">
        <a href="<%= request.getContextPath() %>/view_patron_info/index.jsp" class="linkprofile">Account Summary</a>
        <a href="<%= request.getContextPath() %>/view_patron_info/requests.jsp" class="linkprofile">Requests</a>
        <a href="<%= request.getContextPath() %>/view_patron_info/profile.jsp" class="linkprofile">Profile</a>
        <a href="<%= request.getContextPath() %>/view_patron_info/confirmLogout.jsp" class="linkprofile">Log Out</a>
    </div>
    <div class="lowerbodybox">
        <div id="profilelink-container">
            <a href="<%= request.getContextPath() %>/view_patron_info/profileLogin.jsp" class="submit-link">Change Password</a>
        </div>
        <div class="pageholderbox">
            <h1 id="proftitle">Personal Information</h1>
            <form action="/LibraryManage/save_profile" method="post">
                <label class="nameslabel" id="firstnamelabel">
                    <span class="namespan">First Name:</span>
                    <input type="text" name="fname" class="namesfield" value="${sessionScope.profobj.fname}" required="">
                </label>
                <label class="nameslabel" id="lastnamelabel">
                    <span class="namespan">Last Name:</span>
                    <input type="text" name="lname" class="namesfield" value="${sessionScope.profobj.lname}" required="">
                </label>
                <label id="genderlabel">
                    <span>Gender:</span>
                    <select name="gender" class="genderlist" required="">
                        <option value="Male" ${sessionScope.profobj.gender == 'Male' ? 'selected="selected"' : ''}>Male</option>
                        <option value="Female" ${sessionScope.profobj.gender == 'Female' ? 'selected="selected"' : ''}>Female</option>
                        <option value="N/A" ${sessionScope.profobj.gender == 'N/A' ? 'selected="selected"' : ''}>N/A</option>
                    </select>
                </label>
                <label class="fieldlabel" id="addresslabel">
                    <span class="namespan">Address:</span>
                    <input type="text" name="address" class="profilefield" value="${sessionScope.profobj.address}" required="">
                </label>
                <c:if test="${not empty phoneError}">
                    <div class="error-message">
                        ${phoneError}
                    </div>
                </c:if>
                <label class="fieldlabel" id="phonelabel">
                    <span class="namespan">Phone:</span>
                    <input type="text" name="phone" class="profilefield" value="${sessionScope.profobj.phone}" required="">
                </label>
                <c:if test="${not empty emailError}">
                    <div class="error-message">
                        ${emailError}
                    </div>
                </c:if>
                <label class="fieldlabel" id="emaillabel">
                    <span class="namespan">Email:</span>
                    <input type="text" name="email" class="profilefield" value="${sessionScope.profobj.email}" required="">
                </label>
                <label id="submitprofile">
                    <input type="submit" value="SAVE" id="submitbox">
                </label>
            </form>
                <c:if test="${not empty confirmSaveMessage}">
                        <div id="profile-confirm-update">
                            ${confirmSaveMessage}
                        </div>
                </c:if>
        </div>
    </div>
</body>
</html>
