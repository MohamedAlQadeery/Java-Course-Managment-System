/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BaDlUcK
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private String url ="jdbc:mysql://localhost/";
    private String databaseName="course_system";
    private String username="root";
    private String password="";
    private static Connection connection;
    
    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url+databaseName,username,password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DBConnection getInstance(){
        if (dbConnection==null)
            return dbConnection= new DBConnection();
        else return dbConnection;
    }
    
    public Connection getCon(){
        return this.connection;
    }
    
}
