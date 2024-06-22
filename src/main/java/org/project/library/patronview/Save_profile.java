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
@WebServlet(name = "save_profile", urlPatterns = {"/save_profile"})
public class Save_profile extends HttpServlet {

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
        
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String newemail = request.getParameter("email");
        String newphone = request.getParameter("phone");
        //get table name and old email if new email was entered
        //get profileobj
        HttpSession session = request.getSession();
        Profileobj profobj = (Profileobj) session.getAttribute("profobj");
        String tableName = profobj.getTable();
        String oldemail = profobj.getEmail();
        String oldphone = profobj.getPhone();
        
        //create connection 
        Connection cn = null;
        PreparedStatement ps = null;
        
        try{
            //create connectoin
            cn = getConnection();
            if(validateSQLInput(tableName, "Email", oldemail,newemail,cn, request) &&
                    validateSQLInput(tableName, "Phone", oldphone,newphone,cn,request))
            {
            //set querry to update databse
            String querry = "UPDATE " + tableName + " SET First_Name = ?, "
                    + "Last_Name = ?, Gender = ?, Address = ?, Email = ?,"
                    + "Phone = ? WHERE email = ?";
            ps = cn.prepareStatement(querry);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, gender);
            ps.setString(4, address);
            ps.setString(5, newemail);
            ps.setString(6,newphone);
            ps.setString(7, oldemail);
            ps.executeUpdate();
            //update session profobj with new data
            profobj.setFname(fname);
            profobj.setLname(lname);
            profobj.setGender(gender);
            profobj.setAddress(address);
            profobj.setEmail(newemail);
            profobj.setPhone(newphone);
            session.setAttribute("profobj", profobj);
            request.setAttribute("confirmSaveMessage", "Profile Data Was SAVED!");
            }
            //refresh page
            forwardRequest(request,response,"/view_patron_info/profile.jsp");
            } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Save_profile.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
            if(ps != null){ try{ ps.close(); }catch(SQLException e){e.printStackTrace();}}
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
    private boolean validateSQLInput(String tableName, String columnName, 
            String oldemail, String newemail, Connection cn, 
            HttpServletRequest request) throws SQLException{
        if(!oldemail.equals(newemail)){
           //else if user did change thier phone or email
            //then check if new input has any duplicates on db
            String querry = "SELECT * FROM " + tableName + " "
                    + "WHERE " + columnName + "= ? ";
            try(PreparedStatement ps = cn.prepareStatement(querry)){
                ps.setString(1, newemail);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        if("Email".equals(columnName)){
                            request.setAttribute("emailError", 
                                    "Email already exist in the system");
                        }else{
                            request.setAttribute("phoneError",
                                    "Phone already exist in the system");
                        }
                        return false;
                    }
                }
            }
        }
        return true;    
    }
}
