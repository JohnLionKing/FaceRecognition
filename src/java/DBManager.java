


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/face_recognition";
    
    /**
     * Checking for registered user
     * @param userData  {email, username, password}
     * @param column  
     * @return true : exist, false : new
     * 
     * compare only email
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static boolean isRegistered(Map<String, String> userData, String column) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Connection connection = null;
        int rowCount = 0;

        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String queryString = "SELECT * FROM account WHERE " + column + " = ?";
                    pstatement = connection.prepareStatement(queryString);
                    pstatement.setString(1, userData.get(column));
                    ResultSet rs = pstatement.executeQuery();
                    rowCount = rs.last() ? rs.getRow() : 0;
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        if(rowCount == 0)
        	return false;
        return true;
    }
    
    /**
     * Add Account
     * @param userData  {email, username, password}
     * @return new inserted id
     */
    public static Integer addAccount(Map<String, String> userData) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Connection connection = null;
        Integer result = 0;
        Integer newId = 0;

        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String queryString = "INSERT INTO account (email, username, password) VALUES (?, ?, ?)";
                    pstatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
                    pstatement.setString(1, userData.get("email"));
                    pstatement.setString(2, userData.get("username"));
                    pstatement.setString(3, userData.get("password"));
                    result = pstatement.executeUpdate();
                    
                    ResultSet rs = pstatement.getGeneratedKeys();
                    if (rs.next()){
                    	newId=rs.getInt(1);
                    }
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return newId;
    }
    
    /**
     * Delete Account
     * @param Integer id
     * @return true/false
     */
    public static boolean deleteAccount(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Connection connection = null;
        boolean result = false;

        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String query = "DELETE FROM account where id = ?";
                    pstatement = connection.prepareStatement(query);
                    pstatement.setInt(1, id);
                    result = pstatement.execute();
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Check user is true
     * @param userData  {email, password}
     * @return true/false
     * 
     * compare only email
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static boolean checkUser(Map<String, String> userData) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Connection connection = null;
        int rowCount = 0;

        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String queryString = "SELECT * FROM account WHERE email = ? AND password = ?";
                    pstatement = connection.prepareStatement(queryString);
                    pstatement.setString(1, userData.get("email"));
                    pstatement.setString(2, userData.get("password"));
                    ResultSet rs = pstatement.executeQuery();
                    rowCount = rs.last() ? rs.getRow() : 0;
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        if(rowCount == 0)
        	return false;
        return true;
    }
    
    /**
     * Get all Account Records
     * @param 
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getAllAccountInfo() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Connection connection = null;
        int rowCount = 0;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String queryString = "SELECT * FROM account";
                    pstatement = connection.prepareStatement(queryString);
                    ResultSet rs = pstatement.executeQuery();
                    while (rs.next())
                    {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", rs.getInt("id"));
                        map.put("email", rs.getString("email"));
                        map.put("username", rs.getString("email"));
                        map.put("password", rs.getString("email"));
                        map.put("status", rs.getInt("status"));
                        result.add(map);
                    }
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Get An Account By Email
     * @param 
     * @return Map<String, Object>
     */
    public static Map<String, Object> getAccountInfoById(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Map<String, Object> result = new HashMap<>();
        
        Connection connection = null;
        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String queryString = "SELECT * FROM account WHERE id = ?";
                    pstatement = connection.prepareStatement(queryString);
                    pstatement.setInt(1, id);
                    ResultSet rs = pstatement.executeQuery();
                    while (rs.next())
                    {
                        result.put("id", rs.getInt("id"));
                        result.put("email", rs.getString("email"));
                        result.put("username", rs.getString("username"));
                        result.put("password", rs.getString("password"));
                        result.put("status", rs.getInt("status"));
                    }
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return result;
    }
    
    
    public static Map<String, Object> getAccountInfoByEmail(String email) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        Map<String, Object> result = new HashMap<>();
        
        Connection connection = null;
        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                if(!connection.isClosed())
                {
                    PreparedStatement pstatement = null;
                    String queryString = "SELECT * FROM account WHERE email = ?";
                    pstatement = connection.prepareStatement(queryString);
                    pstatement.setString(1, email);
                    ResultSet rs = pstatement.executeQuery();
                    while (rs.next())
                    {
                        result.put("id", rs.getInt("id"));
                        result.put("email", rs.getString("email"));
                        result.put("username", rs.getString("username"));
                        result.put("password", rs.getString("password"));
                        result.put("status", rs.getInt("status"));
                    }
                    pstatement.close();
                    connection.close();
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return result;
    }
}
