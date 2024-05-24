/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.project.library.authentication;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bryan
 */
@WebServlet(name = "login_authen", urlPatterns = {"/login_authen"})
public class Login_authen extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        //get user input of their username and password
         String username = request.getParameter("username");
         String password = request.getParameter("password");
         //response
         response.setContentType("text/html");
        //connect to library database and try and catch if connection fails
         try{
             Class.forName("com.mysql.cj.jdbc.Driver");
            String URL_path = "jdbc:mysql://localhost:3306/librarydb";
            Connection connect = DriverManager.getConnection(URL_path, "root", "root");
            //set sql statement to check the patron table to validate log in
            PreparedStatement st = connect.prepareStatement("SELECT Username, Password "
                    + "From patroninformation "
                    + "WHERE Username=? AND Password=? ");   
            st.setString(1, username);
            st.setString(2, password);
            ResultSet result = st.executeQuery();
            if(result.next()){
                //patron has has logged in and has been validated
                //code will allow user to access features
                RequestDispatcher rd = request.getRequestDispatcher("/view_patron_info/index.html");
                rd.forward(request, response);
            }else{
                //if no result was found on the patron table then it will check 
                // the staff information table
                st = connect.prepareStatement("SELECT Username, Password "
                    + "From staffinformation "
                    + "WHERE Username=? AND Password=? ");
                result = st.executeQuery();
                if(result.next()){
                    //send staff to staff page
                    response.sendRedirect("");
                }else{
                    //this code will display message that username and password is not valid
                    request.getRequestDispatcher("/index.html").forward(request, response);
                }
            }
            connect.close();
         }catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Login_authen.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
