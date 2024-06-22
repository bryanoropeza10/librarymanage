<%-- 
    Document   : requests
    Created on : May 29, 2024, 11:23:14 PM
    Author     : bryan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Requests Page</title>
        <link rel="stylesheet" href="/LibraryManage/CSS/patron_menu.css" type="text/css" />
    </head>
    <body>
        <div class="headerbox">
            <a href="<%= request.getContextPath() %>/view_patron_info/index.jsp" class="linkprofile">Account Summary</a>
            <a href="<%= request.getContextPath() %>/request_list" class="linkprofile">Requests</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/profile.jsp" class="linkprofile">Profile</a>
            <a href="<%= request.getContextPath() %>/view_patron_info/confirmLogout.jsp" class="linkprofile">Log Out</a>
        </div>
        <div class="lowerbodybox">
            <div class="pageholderbox">
                <h1 id="requestTitle">Request</h1>
                <div id="requestPageDiv">
                    <a id="requestBookButton" href="<%=request.getContextPath()%>/view_patron_info/requestBook.jsp">Request A Book</a>
                </div>
                <div >
                <table id="bookTable">
                    <tr>
                        <th scope="col">ISBN</th>
                        <th scope="col">Title</th>
                        <th scope="col">Author</th>
                        <th scope="col">Format</th>
                        <th scope="col">Checkout Date</th>
                        <th scope="col">Due Date</th>
                        <th scope="col">Status</th>
                    </tr>
                    <c:forEach var="book" items="${bookList}">
                        <tr>
                            <td>${book.isbn}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.bookformat}</td>
                            <td>${book.madedate}</td>
                            <td>${book.duedate}</td>
                            <td>${book.status}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
