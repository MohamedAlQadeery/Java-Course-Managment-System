/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import DB.CourseCenterFacade;
import Model.User;
import Views.Admin.HomeView;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author BaDlUcK
 */
public class AdminHomeViewController {

    HomeView HomeView;
    User user;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();

    
    public void initView(){
        setWelcomeLabel();
        setAppInfoJLabel();
        setImageLabel();
    }
    
    private void setWelcomeLabel() {
        HomeView.getWelcomeJLabel().setText("Welcome " + this.user.getName());
    }

    private void setAppInfoJLabel() {
        HomeView.getAppInfoJLabel().setText("<html>Number of Users In The Application :"+courseCenterFacade.getCountUsers()+"<br> Number of Courses :"+courseCenterFacade.getCourseCount()+"<br/></html>");
    }
    
    private void setImageLabel(){
        ImageIcon imageIcon = new ImageIcon(user.getUser_image());
        Image image = imageIcon.getImage().getScaledInstance(HomeView.getImageJLabel().getWidth(),HomeView.getImageJLabel().getHeight(),Image.SCALE_SMOOTH);
        
        HomeView.getImageJLabel().setIcon(new ImageIcon(image));
                
    }

    public AdminHomeViewController(HomeView HomeView, User user) {
        this.HomeView = HomeView;
        this.user = user;
    }

}
