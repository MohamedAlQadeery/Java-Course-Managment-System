/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Student;

import Controller.Teacher.*;
import DB.CourseCenterFacade;
import Model.Course;
import Model.User;
import Views.Student.StudentHomeView;
import Views.Teacher.TeacherHomeView;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author BaDlUcK
 */
public class StudentHomeViewController {

    User user;
    StudentHomeView studentHomeView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    protected ArrayList<Course> courses;

    public StudentHomeViewController(User user, StudentHomeView studentHomeView) {
        this.user = user;
        this.studentHomeView = studentHomeView;
        this.courses = courseCenterFacade.getStudentCourses(this.user);
        this.user.setCourses(courses);
    }

    public void initView() {
        setWelcomeLabel();
        setUserInfoJLabel();
        setImageLabel();
    }

    private void setWelcomeLabel() {
        studentHomeView.getWelcomeJLabel().setText("Welcome Student " + this.user.getName());
    }

    private void setUserInfoJLabel() {

        studentHomeView.getStudentInfoJLabel().setText("Choose Option up ");

    }

    public void showGradesAction() {
        if (studentHomeView.getShowGradesJButton().getText().equalsIgnoreCase("Show Grades")) {
            studentHomeView.getStudentInfoJLabel().setText(this.userGrades());
            studentHomeView.getShowGradesJButton().setText("Hide Grades");

        } else {
            studentHomeView.getStudentInfoJLabel().setText("");
            studentHomeView.getShowGradesJButton().setText("Show Grades");
        }

    }

    public void showInfoAction() {
        if (studentHomeView.getShowInfoJButton().getText().equalsIgnoreCase("Show Info")) {
            studentHomeView.getStudentInfoJLabel().setText(this.user.userInfo());
            studentHomeView.getShowInfoJButton().setText("Hide Info");

        } else {
            studentHomeView.getStudentInfoJLabel().setText("");
            studentHomeView.getShowInfoJButton().setText("Show Info");
        }
    }

    private void setImageLabel() {
        ImageIcon imageIcon = new ImageIcon(user.getUser_image());
        Image image = imageIcon.getImage().getScaledInstance(studentHomeView.getImageJLabel().getWidth(), studentHomeView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
        studentHomeView.getImageJLabel().setIcon(new ImageIcon(image));

    }

    private String userGrades() {
        String userGrades = "<html>";
        HashMap<String, Double> userCourses = courseCenterFacade.getStudentCoursesWithGrades(user);
        for(String courseName :userCourses.keySet()){
            userGrades+=courseName+" : "+userCourses.get(courseName)+"<br/>";
        }
        return userGrades;
    }
}
