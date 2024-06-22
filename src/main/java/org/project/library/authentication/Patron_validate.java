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
@WebServlet(name = "patron_validate", urlPatterns = {"/patron_validate"})
public class Patron_validate extends HttpServlet {


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
          //get parameter values
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        //connections to database
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rt = null;
        
        //connect to database and add new data
        try{
            //get connection
            cn = getConnection();

            //check if user exist
            st = cn.prepareStatement("SELECT * "
                    + "FROM patroninformation "
                    + "WHERE Email = ? or phone = ?");
            st.setString(1,email);
            st.setString(2, phone);
            rt = st.executeQuery();
            if(rt.next()){
                //send message to user on duplicate info in database
                request.setAttribute("errorMessage", 
                        "(Email or phone number already exists. Please use different information.)");
                //send user back to reenter info 
                forwardRequest(request, response, "/login_info/createAccount.jsp");
            }else{
                //create profileobj 
                Profileobj profobj = new Profileobj(fname, lname, gender, address, email, phone, "patroninformation");
                //create httpsession and store profileobj into session 
                HttpSession session = request.getSession();
                session.setAttribute("profobj", profobj);
                //send user to enter password in the createlogin.jsp
                forwardRequest(request,response,"/login_info/createLogin.jsp");
            }
        }catch(ClassNotFoundException | SQLException ex){
                Logger.getLogger(Login_authen.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
            if(st != null){ try{ st.close(); }catch(SQLException e){e.printStackTrace();}}
            if(rt != null){ try{ rt.close(); }catch(SQLException e){e.printStackTrace();}}
        }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url_Path = "jdbc:mysql://localhost:3306/librarydb";
        return DriverManager.getConnection(url_Path, "root", "root");
    }
    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
