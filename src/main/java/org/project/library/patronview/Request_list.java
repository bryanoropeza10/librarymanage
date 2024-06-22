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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.project.library.authentication.Profileobj;


/**
 *
 * @author bryan
 */
@WebServlet(name = "request_list", urlPatterns = {"/request_list"})
public class Request_list extends HttpServlet {
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
        //get session
        HttpSession session = request.getSession(false);
        Profileobj profobj = (Profileobj)session.getAttribute("profobj");
        
        //create connection
        Connection cn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try{
            //call connection
            cn = getConnection();
            //get patron id
            int patron_id = profobj.getID();
            //create list container
            List<Bookobj> bookList = new ArrayList<>();
            //create sql querry
            String querry = "SELECT inv.ISBN, inv.Title, inv.Author, inv.Format, "
                    + "loan.OverDue_status, loan.Date_Checkout, loan.Due_Date " +
                    "FROM patroninformation as patron " +
                    "INNER JOIN bookloans AS loan " +
                    "ON patron.Patron_ID = loan.Patron_ID " +
                    "INNER JOIN bookinventory AS inv " +
                    "ON loan.ISBN = inv.ISBN " +
                    "WHERE patron.Patron_ID = ?";
            ps = cn.prepareStatement(querry);
            ps.setInt(1, patron_id);
            rs = ps.executeQuery();
            while(rs.next()){
                Bookobj bookobj = new Bookobj();
                bookobj.setIsbn(rs.getString("ISBN"));
                bookobj.setAuthor(rs.getString("Author"));
                bookobj.setTitle(rs.getString("Title"));
                bookobj.setBookformat(rs.getString("Format"));
                bookobj.setDuedate(rs.getString("Due_Date"));
                bookobj.setMadedate(rs.getString("Date_Checkout"));
                bookobj.setStatus(rs.getString("OverDue_status"));
                bookList.add(bookobj);
            }
            request.setAttribute("bookList", bookList);
            request.setAttribute("profobj", profobj);
            forwardRequest(request,response,"/view_patron_info/requests.jsp");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Request_list.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(cn != null){ try{ cn.close(); }catch(SQLException e){e.printStackTrace();}}
            if(rs != null){ try{ rs.close(); }catch(SQLException e){e.printStackTrace();}}
            if(ps != null){ try{ ps.close(); }catch(SQLException e){e.printStackTrace();}}
        }
    }
    
    private Connection getConnection() 
            throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url_Path = "jdbc:mysql://localhost:3306/librarydb";
        return DriverManager.getConnection(url_Path,"root","root");
    }
    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
