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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bryan
 */
@WebServlet(name = "securitycode_validation", urlPatterns = {"/securitycode_validation"})
public class SecurityCode_validation extends HttpServlet {
     private final EmailService emailService = new EmailService();
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

        if (session != null) {
            Profileobj profobj = (Profileobj) session.getAttribute("profobj");
            if (profobj != null) {
                String email = profobj.getEmail();
                String numbersfield = getNumbersField(request);
                Connection cn = null;

                try {
                    cn = getConnection();
                    if (isEmailInDatabase(cn, email, "patroninformation")) {
                        handleUserRequest(cn, request, response, session, email, numbersfield, "patroninformation", profobj);
                    } else if (isEmailInDatabase(cn, email, "staffinformation")) {
                        handleUserRequest(cn, request, response, session, email, numbersfield, "staffinformation", profobj);
                    } else {
                        forwardRequest(request, response, "/login_info/recoverLogin.jsp");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Recover_login.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
                }
            }
        } else {
            forwardRequest(request, response, "/login_info/recoverLogin.jsp");
        }
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    
    public String generateCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);//genereate a 6 digit code
        return String.valueOf(code);
    }
    
     private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url_Path = "jdbc:mysql://localhost:3306/librarydb";
        return DriverManager.getConnection(url_Path, "root", "root");
    }
     
      private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
    
     private String getNumbersField(HttpServletRequest request) {
        String numbersfield = null;
        if (request.getParameter("1number") != null) {
            numbersfield = request.getParameter("1number") + request.getParameter("2number") + request.getParameter("3number")
                    + request.getParameter("4number") + request.getParameter("5number") + request.getParameter("6number");
        }
        return numbersfield;
    }
     
     private boolean isEmailInDatabase(Connection cn, String email, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE Email = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rt = ps.executeQuery()) {
                return rt.next();
            }
        }
    }
     
      private void handleUserRequest(Connection cn, HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                   String email, String numbersfield, String tableName, Profileobj profobj) throws SQLException, ServletException, IOException {
        if (numbersfield != null) {
            String query = "SELECT * FROM " + tableName + " WHERE Email = ?";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, email);
                try (ResultSet rt = ps.executeQuery()) {
                    if (rt.next() && numbersfield.equals(rt.getString("Recover_Code"))) {
                        profobj.setTable(tableName);
                        session.setAttribute("profobj", profobj);
                        forwardRequest(request, response, "/login_info/resetPassword.jsp");
                        return;
                    }
                }
            }
            request.setAttribute("errorMessage", "Incorrect code! "
                    + "(Enter security code again, or Click on the link to resend you a different code.)");
            forwardRequest(request, response, "/login_info/recoveryCode.jsp");
        } else {
            updateRecoveryCodeAndSendEmail(cn, session, email, tableName);
            forwardRequest(request, response, "/login_info/recoveryCode.jsp");
        }
    }
      
       private void updateRecoveryCodeAndSendEmail(Connection cn, HttpSession session, String email, String tableName) throws SQLException {
        String recoveryCode = generateCode();
        String query = "UPDATE " + tableName + " SET Recover_Code = ? WHERE Email = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, recoveryCode);
            ps.setString(2, email);
            ps.executeUpdate();
        }
        Profileobj profobj = (Profileobj) session.getAttribute("profobj");
        session.setAttribute("profobj", profobj);
        emailService.sendRecoveryCode(email, recoveryCode);
    } 
}
