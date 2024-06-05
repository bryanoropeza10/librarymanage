package org.project.library.authentication;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
@WebServlet(name = "recover_login", urlPatterns = {"/recover_login"})
public class Recover_login extends HttpServlet {

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
         String useremail = request.getParameter("email");

        try (Connection cn = getConnection()) {
            String recoveryCode = generateCode();
            if (updateRecoveryCode(cn, useremail, recoveryCode, "patroninformation") || updateRecoveryCode(cn, useremail, recoveryCode, "staffinformation")) {
                createSession(request, useremail);
                emailService.sendRecoveryCode(useremail, recoveryCode);
                forwardRequest(request, response, "/login_info/recoveryCode.jsp");
            } else {
                request.setAttribute("errorMessage", "(Email Does Not Exist In Our Records.)");
                forwardRequest(request, response, "/login_info/recoverLogin.jsp");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Recover_login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     private void createSession(HttpServletRequest request, String email) {
        Profileobj profobj = new Profileobj();
        profobj.setEmail(email);
        HttpSession session = request.getSession();
        session.setAttribute("profobj", profobj);
    }
     
     private boolean updateRecoveryCode(Connection cn, String email, String recoveryCode, String tableName) throws SQLException {
        String selectQuery = "SELECT * FROM " + tableName + " WHERE Email = ?";
        String updateQuery = "UPDATE " + tableName + " SET Recover_Code = ? WHERE Email = ?";

        try (PreparedStatement selectPS = cn.prepareStatement(selectQuery)) {
            selectPS.setString(1, email);
            try (ResultSet rt = selectPS.executeQuery()) {
                if (rt.next()) {
                    try (PreparedStatement updatePS = cn.prepareStatement(updateQuery)) {
                        updatePS.setString(1, recoveryCode);
                        updatePS.setString(2, email);
                        updatePS.executeUpdate();
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
