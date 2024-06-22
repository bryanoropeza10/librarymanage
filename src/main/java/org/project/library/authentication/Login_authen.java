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
        //connect to library database and try and catch if connection fails
        Connection cn = null;
        
         try{
             cn = getConnection();
            if(validateLogin(cn, "patroninformation", username, password, request)){
                //patron has has logged in and has been validated
                //code will allow user to access features
                forwardRequest(request, response, "/view_patron_info/index.jsp");
            }else if(validateLogin(cn,"staffinformation", username, password, request)){
                //Staff has has logged in and has been validated
                //code will allow user to access features
                forwardRequest(request,response,"/LibraryManage/index.jsp");
            }else{
                request.setAttribute("errorMessage", "Username/Password is incorrect");
                forwardRequest(request, response, "/login_info/index.jsp");
            }
         }catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Login_authen.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
         }
    }
    
     private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
     private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url_Path = "jdbc:mysql://localhost:3306/librarydb";
        return DriverManager.getConnection(url_Path, "root", "root");
    }
     private boolean validateLogin(Connection cn, String tableName, String username, String password, HttpServletRequest request)
             throws SQLException{
         String querry = "SELECT * "
                    + "From " + tableName + " "
                    + "WHERE Username=? AND Password=? ";
         try(PreparedStatement ps = cn.prepareStatement(querry)){
             ps.setString(1, username);
             ps.setString(2, password);
             try(ResultSet rs = ps.executeQuery()){
                 if(rs.next()){
                     Profileobj profobj = new Profileobj();
                     profobj.setID(rs.getInt("Patron_ID"));
                     profobj.setFname(rs.getString("First_Name"));
                     profobj.setLname(rs.getString("Last_Name"));
                     profobj.setGender(rs.getString("Gender"));
                     profobj.setEmail(rs.getString("Email"));
                     profobj.setPhone(rs.getString("Phone"));
                     profobj.setAddress(rs.getString("Address"));
                     profobj.setTable(tableName);
                     profobj.setUsername(username);
                     HttpSession session = request.getSession(true);
                     session.setAttribute("profobj", profobj);
                     return true;
                 }
             }
         }
         return false;
     }

}
