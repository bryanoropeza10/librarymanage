/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.project.library.patronview;

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
import org.project.library.authentication.Profileobj;


/**
 *
 * @author bryan
 */
@WebServlet(name = "save_password", urlPatterns = {"/save_password"})
public class Save_password extends HttpServlet {


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
        //get session and set attribute and set connection
        HttpSession session = request.getSession();
        Profileobj profobj = (Profileobj)session.getAttribute("profobj");
        Connection cn = null;
        //get parameter values
        String oldpass = request.getParameter("oldpassword");
        String newpass = request.getParameter("newpassword");
        String repass = request.getParameter("repassword");
        
        try{
             //get connection 
            cn = getConnection();
            //check oldpassword matches new password
            if(checkOldPassword(cn,oldpass,profobj)){
                if(newpass.equals(repass)){
                    setNewPassword(cn,profobj,newpass);
                    request.setAttribute("confirmPasswordMessage", "Password Has Been Changed!");
                }else{
                    request.setAttribute("newPasswordMatchError", "New Passwords Must Match");
                }
            }else{
                 request.setAttribute("oldPasswordMatchError", "Incorrect Password");
            }       
            request.setAttribute("profobj", profobj);
            forwardRequest(request,response,"/view_patron_info/profileLogin.jsp");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Save_password.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
        }
    }
    
    
    private Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url_Path = "jdbc:mysql://localhost:3306/librarydb";
        return DriverManager.getConnection(url_Path,"root","root");
    }
    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) 
            throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
    private void setNewPassword(Connection cn, Profileobj profobj, String password) 
            throws SQLException{
        String tableName = profobj.getTable();
        String email = profobj.getEmail();
        String querry = "UPDATE " + tableName + " SET Password = ? WHERE Email = ?";
        try(PreparedStatement ps = cn.prepareStatement(querry)){
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }
    private boolean checkOldPassword(Connection cn, String old, Profileobj profobj) 
            throws SQLException{
        String tableName = profobj.getTable();
        String email = profobj.getEmail();
        String querry = "SELECT Password FROM " + tableName + " Where Email = ?";
        try(PreparedStatement ps = cn.prepareStatement(querry)){
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next() && rs.getString("Password").equals(old)){
                    return true;
                }
            }
        }
        return false;
    }
}
