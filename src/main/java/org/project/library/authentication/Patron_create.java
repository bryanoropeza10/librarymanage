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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        
        //does account already exist in the database
        boolean accountExist = false;
        
        //connect to database and add new data
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url_Path = "jdbc:mysql://localhost:3306/librarydb";
            Connection connect = DriverManager.getConnection(url_Path, "root", "root");

            //check if user exist
            PreparedStatement st = connect.prepareStatement("SELECT * "
                                                            + "FROM patroninformation "
                                                            + "WHERE Email = ? and phone = ?");
            st.setString(1,email);
            st.setString(2, phone);
            ResultSet result = st.executeQuery();
            if(result.next()){
                accountExist=true;
            }
            if(!accountExist){
                if(password.equals(repassword)){
                    //add login information in the database 
                    //and confirm if password enter is matched
                     //sql to create a new row of data in the patron database
                    st = connect.prepareStatement("INSERT INTO patroninformation "
                    + "(First_Name, Last_Name, Address, Email, Phone, Username, Password, Total_Fines, Patron_Status, Overdue_Books, Books_Onhand) "
                    + "Values "
                    + "(?,?,?,?,?,?,?,?,?,?,?)");
                    st.setString(1, fname);
                    st.setString(2, lname);
                    st.setString(3, address);
                    st.setString(4, email);
                    st.setString(5, phone);
                    st.setString(6, username);
                    st.setString(7, password);
                    st.setDouble(8, 0.0);
                    st.setString(9, "Good Standing");
                    st.setInt(10, 0);
                    st.setInt(11, 0);
                    //send user to the menu if update was succesfull
                    if(st.executeUpdate() == 1){
                        RequestDispatcher rd = request.getRequestDispatcher("/view_patron_info/index.jsp");
                        rd.forward(request, response);
                    }else{
                        System.out.println("failed to update database");
                    }
                }else{
                    //send user back to the login page to reenter password info
                    RequestDispatcher rd = request.getRequestDispatcher("/login_info/createAccount.jsp");
                    rd.forward(request, response); 
                }
            }else{
                //account already exist
                //send message to user that account exist
                //send user back to the login page to reenter password info
                    RequestDispatcher rd = request.getRequestDispatcher("/login_info/createAccount.jsp");
                    rd.forward(request, response); 
                
            }
        }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(Login_authen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  

}
