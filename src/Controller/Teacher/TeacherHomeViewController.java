/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Teacher;

import DB.CourseCenterFacade;
import Model.Course;
import Model.User;
import Views.Teacher.TeacherHomeView;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author BaDlUcK
 */
public class TeacherHomeViewController {
    User user;
    TeacherHomeView teacherHomeView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
 protected ArrayList<Course> courses ;

    public TeacherHomeViewController(User user, TeacherHomeView teacherHomeView) {
        this.user = user;
        this.teacherHomeView = teacherHomeView;
        this.courses = courseCenterFacade.getTeacherCourses(this.user.getId());
        this.user.setCourses(courses);
    }
    
      public void initView(){
        setWelcomeLabel();
        setUserInfoJLabel();
        setImageLabel();
    }
    
    private void setWelcomeLabel() {
        teacherHomeView.getWelcomeJLabel().setText("Welcome Teacher " + this.user.getName());
    }

    private void setUserInfoJLabel() {
        
        teacherHomeView.getTeacherInfoJLabel().setText(this.user.userInfo());
    }
    
    private void setImageLabel(){
        ImageIcon imageIcon = new ImageIcon(user.getUser_image());
        Image image = imageIcon.getImage().getScaledInstance(teacherHomeView.getImageJLabel().getWidth(),teacherHomeView.getImageJLabel().getHeight(),Image.SCALE_SMOOTH);
        teacherHomeView.getImageJLabel().setIcon(new ImageIcon(image));
                
    }
    
    
}
