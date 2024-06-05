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
import jakarta.servlet.http.HttpSession;
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
        
        HttpSession session = request.getSession(false);
        if(session != null){
            Profileobj profobj = (Profileobj)session.getAttribute("profobj");
        if(profobj != null){
            
        //get parameter values
        String fname = profobj.getFname();
        String lname = profobj.getLname();
        String gender = profobj.getGender();
        String address = profobj.getAddress();
        String email = profobj.getEmail();
        String phone = profobj.getPhone();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        
        //connections to database
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rt = null;
        
        //connect to database and add new data
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url_Path = "jdbc:mysql://localhost:3306/librarydb";
            con = DriverManager.getConnection(url_Path, "root", "root");

            //check if user exist
            st = con.prepareStatement("SELECT * "
                    + "FROM patroninformation "
                    + "WHERE Username = ?");
            st.setString(1,username);
            rt = st.executeQuery();
            if(rt.next()){
                //send message to user that their username exist
                request.setAttribute("errorMessage", 
                        "(Username already exists. Please use a different Username.)");
                //send user back to createLogin.jsp with a message
                RequestDispatcher rd = request.getRequestDispatcher("/login_info/createLogin.jsp");
                rd.forward(request, response);
            }else{
                if(!password.equals(repassword)){
                    //send message that password does not match
                    request.setAttribute("errorMessage", 
                        "(Password does not Match. Please enter a matching password.)");
                    //send user back to the login page to reenter password info
                    RequestDispatcher rd = request.getRequestDispatcher("/login_info/createLogin.jsp");
                    rd.forward(request, response); 
                }else{
                    //add login information in the database 
                    //and confirm if password enter is matched
                     //sql to create a new row of data in the patron database
                    st = con.prepareStatement("INSERT INTO patroninformation "
                    + "(First_Name, Last_Name, Address, Email, Phone, Username, Password, Total_Fines, Patron_Status, Overdue_Books, Books_Onhand, Gender) "
                    + "Values "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)");
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
                    st.setString(12, gender);
                    //send user to the menu if update was succesfull
                    if(st.executeUpdate() == 1){    
                        //update the profobj in the session
                        session.setAttribute("profobj", profobj);
                        //send user to the menu page
                        RequestDispatcher rd = request.getRequestDispatcher("/view_patron_info/index.jsp");
                        rd.forward(request, response);
                    }else{
                        System.out.println("failed to update database");
                    }
                }
            } 
        }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(Login_authen.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(con != null){ try{ con.close(); }catch(SQLException e){e.printStackTrace();}}
            if(st != null){ try{ st.close(); }catch(SQLException e){e.printStackTrace();}}
            if(rt != null){ try{ rt.close(); }catch(SQLException e){e.printStackTrace();}}
        }
        }
        }
    }
}
