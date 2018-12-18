/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.AppFactory;
import Model.Course;
import Model.User;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author BaDlUcK
 */
public class DbUser {

    AppFactory appFactory = AppFactory.getAppFactory();
    DBConnection dBConnection = DBConnection.getInstance();
    Connection con = dBConnection.getCon();
    PreparedStatement ps;
    ResultSet rs;

    //User Queries
    private final String LOGIN_QUERY = "select * from users where username = ? and password =?";
    private final String CREATE_USER = "insert into users (username,password,name,email,type,image_path,image) values (?,?,?,?,?,?,?)";
    private final String UPDATE_USER = "update users set username = ? ,password = ?,name = ?,email = ?,type =?,image_path = ?,image = ? where id = ?";
    private final String DELETE_USER = "delete from users where id = ? ";
    private final String GET_USER = "select * from users where id = ?";

    private final String GET_ALL_USERS = "select * from users where type =?";

    private final String SEARCH_USERS = "select * from users where type = ? and name like ?";

    private final String GET_COUNT_USERS = "select count(*) from users";

    public User login(String username, String password) {
        if (validateEmptyValues(username, password)) {
            try {
                ps = con.prepareStatement(LOGIN_QUERY);
                ps.setString(1, username);
                ps.setString(2, password);

                rs = ps.executeQuery();
                if (rs.first()) {
                    return getUser(rs.getInt("id"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public int create_user(User user) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(CREATE_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getUser_type());
            ps.setString(6, user.getImage_path());
            InputStream in = new FileInputStream(new File(user.getImage_path()));
            ps.setBlob(7, in);

            affectedRows = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public ArrayList<User> get_all_teachers(String type) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_ALL_USERS);
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getString("email"),
                        rs.getString("image_path"), ImageIO.read(rs.getBinaryStream("image")), rs.getString("type")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public User getUser(int id) {
        try {
            ps = con.prepareStatement(GET_USER);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                User user = appFactory.createUser();
                user.initUser(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getString("email"),
                        rs.getString("image_path"), ImageIO.read(rs.getBinaryStream("image")), rs.getString("type")
                );
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean validateEmptyValues(String firstStr, String secStr) {
        if (firstStr.equals("") || secStr.equals("")) {
            return false;
        }
        return true;
    }

    public ArrayList<User> search_user(String type, String name) {
        ArrayList<User> teachers = new ArrayList<>();

        try {
            ps = con.prepareStatement(SEARCH_USERS);
            ps.setString(1, type);
            ps.setString(2, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = appFactory.createUser();
                user.initUser(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getString("email"),
                        rs.getString("image_path"), ImageIO.read(rs.getBinaryStream("image")), rs.getString("type")
                );
                teachers.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }

    public int update_user(User user) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getUser_type());
            ps.setString(6, user.getImage_path());
            InputStream in = new FileInputStream(new File(user.getImage_path()));
            ps.setBlob(7, in);

            ps.setInt(8, user.getId());

            affectedRows = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public int delete_user(int id) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(DELETE_USER);
            ps.setInt(1, id);
            affectedRows = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public int countUsers() {
        try {
            ps = con.prepareCall(GET_COUNT_USERS);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public HashMap<String, Integer> getUsersHashMap(String type) {
        HashMap<String, Integer> users = new HashMap<String, Integer>();
        try {
            ps = con.prepareStatement(GET_ALL_USERS);
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                users.put(rs.getString("name"), rs.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

}
