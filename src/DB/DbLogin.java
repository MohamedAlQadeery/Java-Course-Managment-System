/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BaDlUcK
 */
public class DbLogin {

    DBConnection dBConnection = DBConnection.getInstance();
    Connection con = dBConnection.getCon();
    PreparedStatement ps;
    ResultSet rs;

    //login query
    private final String loginQuery = "select * from login_info where username = ? and password = ?";

    public int login(String username, String password) {
        if (verifyEmptyFields(username, password)) {
            try {
                ps = con.prepareStatement(loginQuery);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.first()) {
                    return rs.getInt("user_id");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DbLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return -1;
    }

    private boolean verifyEmptyFields(String firstString, String secString) {
        if (firstString.equals("") || secString.equals("")) {
            return false;
        }
        return true;

    }

}
