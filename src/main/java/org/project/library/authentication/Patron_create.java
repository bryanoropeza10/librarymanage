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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bryan
 */
@WebServlet(name = "patron_create", urlPatterns = {"/patron_create"})
public class Patron_create extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get parameter values
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        //connect to database and add new data
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url_Path = "jdbc:mysql://localhost:3306/librarydb";
            Connection connect = DriverManager.getConnection(url_Path, "root", "root");
            
            //sql to create a new row of data in the patron database
            PreparedStatement st = connect.prepareStatement("INSERT INTO patroninformation "
                    + "(First_Name, Last_Name, Address, Email, Phone) "
                    + "Values "
                    + "(?,?,?,?,?)");
            st.setString(1, fname);
            st.setString(2, lname);
            st.setString(3, address);
            st.setString(4, email);
            st.setString(5, phone);
            
            if(st.executeUpdate() == 1){
                RequestDispatcher rd = request.getRequestDispatcher("/login_info/createLogin.html");
                rd.forward(request, response);
            }else{
                //give user a messae that information already exist in the database
                System.out.println("failled to update database");
            }
        }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(Login_authen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
