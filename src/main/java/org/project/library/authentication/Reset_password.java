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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bryan
 */
@WebServlet(name = "reset_password", urlPatterns = {"/reset_password"})
public class Reset_password extends HttpServlet {
    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession session = request.getSession(false);
       String password = request.getParameter("password");
       String repassword = request.getParameter("repassword");
       
       
       if(session != null){
           Profileobj profobj = (Profileobj) session.getAttribute("profobj");
           if(profobj != null){
               String tableName = profobj.getTable();
               String email = profobj.getEmail();
               Connection cn = null;

              
               try{
                   //get connection
                   cn = getConnection();
                   if(password.equals(repassword)){
                       updatePassword(cn, password, tableName, email);
                       session.setAttribute("profobj", profobj);
                       forwardRequest(request, response, "/login_info/index.jsp");
                   }else{
                       request.setAttribute("errorMessage", "Entered Password Must Match");
                       forwardRequest(request, response,"/login_info/resetPassword.jsp");
                   }
               }catch(ClassNotFoundException | SQLException e){
                   Logger.getLogger(Recover_login.class.getName()).log(Level.SEVERE, null, e);
               }finally{
                   if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
                   session.invalidate();
               }
           }
       }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url_Path = "jdbc:mysql://localhost:3306/librarydb";
        return DriverManager.getConnection(url_Path, "root","root");
    }
    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) 
            throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
    
    private void updatePassword(Connection cn, String password, String tableName, String email) 
            throws ClassNotFoundException, SQLException{
        String querry = "UPDATE " + tableName + " SET Password= ? WHERE Email = ?";
        try(PreparedStatement ps = cn.prepareStatement(querry)){
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }
}
