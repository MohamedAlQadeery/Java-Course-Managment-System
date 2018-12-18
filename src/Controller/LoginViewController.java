/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.CourseCenterFacade;
import Model.User;
import Views.LoginView;
import javax.swing.JOptionPane;

/**
 *
 * @author BaDlUcK
 */
public class LoginViewController {

    LoginView loginView;
    User user;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();

    public LoginViewController(LoginView loginView) {
        this.loginView = loginView;

    }

    public User loginAction() {
        String username = loginView.getUsernameJTextfield().getText();
        String password = loginView.getPasswordJPasswordField().getText();
        user = courseCenterFacade.AppLogin(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(loginView, "You Logged in Successfully !");
            return user;
        } else {
            JOptionPane.showMessageDialog(loginView, "Wrong username/password !");
        }
        return null;
    }
}
