

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Microsoft
 */
@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>"); 
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] faces = request.getParameterValues("faces[]");
        
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("email", email);
        userData.put("password", password);
        
        try {
            if(DBManager.checkUser(userData)) {
                Map<String, Object> accountInfo = DBManager.getAccountInfoByEmail(email);
                
                Map<String, Integer> matchedInfo = Validate.checkUser(faces);
                Integer mostPredictId = Integer.valueOf(matchedInfo.get("mostPredictId").toString());
                Integer maxCount = Integer.valueOf(matchedInfo.get("maxCount").toString());
                
                if(!accountInfo.isEmpty() && accountInfo.get("id").toString().compareTo(mostPredictId.toString()) == 0 && maxCount >= 5)
                {
                   out.println("Email and Password are correct\nWelcome " + accountInfo.get("username"));
                   return;
                } else {
                    out.println("Your Face incorrect");
                    return;
                }
            } else {
                out.println("Your Email or Password incorrect");
                return;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return;
    }
}
