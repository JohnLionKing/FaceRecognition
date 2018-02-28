

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author SamRasmey
 */
@WebServlet(urlPatterns = {"/register"})
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] faces = request.getParameterValues("faces[]");
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        
        Map<String, String> userData = new HashMap<String, String>();
         userData.put("email", email);
        userData.put("username", username); 
        userData.put("password", password);
        Map<String, Integer> matchedInfo = Validate.checkUser(faces);
        Integer mostPredictId = Integer.valueOf(matchedInfo.get("mostPredictId").toString());
        Integer maxCount = Integer.valueOf(matchedInfo.get("maxCount").toString());
        try {
            if(mostPredictId == -1 || maxCount < 5) {

                if(DBManager.isRegistered(userData, "email")) {
                    out.print("Registration failed\n");
                    out.print("Email already exists\n");
                    out.println("Please enter another email address");
                    return;
                }
                if(DBManager.isRegistered(userData, "username")) {
                    out.print("Registration failed\n");
                    out.print("Username already exists\n");
                    out.println("Please enter another email address");
                    return;
                }
                Integer newInsertedId = DBManager.addAccount(userData);
                if(newInsertedId != 0)					
                {
                    int index = 0;
                    for(String face : faces) {
                       temp2Real(newInsertedId);
                    }
                    out.println("Registration completed successfully");
                    //DBManager.deleteAccount(newInsertedId);
                }
                
            } else {
                Map<String, Object> predictAccountInfo = DBManager.getAccountInfoById(mostPredictId);
                out.println("You are already registerd as " + predictAccountInfo.get("username").toString() + ".");
                return;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
        }
    }
    
    public boolean temp2Real(int newInsertedId) throws IOException {
        String tempImageFolder = FaceRecognition.tempImageFolder;
        String imageDataFolder = FaceRecognition.imageDataFolder;

        for(int i = 0; i < 10; i++) {
            File src = new File(tempImageFolder + "temp_" + i + ".png");
            File dst = new File(imageDataFolder + "" + newInsertedId + "_" + i + ".png");
            Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            src = null;
            dst = null;
        }
        
        return false;
    }
}
