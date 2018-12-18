/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import DB.CourseCenterFacade;
import Model.AppLogic.CourseModel;
import Model.Course;
import Model.User;
import Views.Admin.ManageCourseView;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BaDlUcK
 */
public class ManageCourseViewController {

    User user;
    Course course;
    CourseModel courseModel ; // for observer
    ManageCourseView manageCourseView;
    DefaultTableModel defaultTableModel;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    HashMap<String, Integer> users = courseCenterFacade.getUsersHashMap("teacher");

    private ArrayList<User> teachers = courseCenterFacade.getAllUsers("teacher");

    public ManageCourseViewController(ManageCourseView manageCourseView) {
        this.manageCourseView = manageCourseView;
        defaultTableModel = (DefaultTableModel) this.manageCourseView.getCoursesJTabel().getModel();
        courseModel = new CourseModel();
        courseModel.addObserver(manageCourseView);
    }

    public void initView() {
        initTable();
        initTeacherJComoboBox();
    }

    public void initTable() {
        ArrayList<Course> courses = courseCenterFacade.getAllCourses();
        for (Course course : courses) {
            User courseTeacher = courseCenterFacade.getUser(course.getTeacher_id());
            defaultTableModel.addRow(new Object[]{course.getId(), course.getCourse_name(), courseTeacher.getName(), course.getNumOfStudents() + "/" + course.getCapacity()});
        }

    }

    public void initTeacherJComoboBox() {
        for (String teacherName : users.keySet()) {
            manageCourseView.getTeachersjComboBox1().addItem(teacherName);
        }
    }

    public void mouseClickedAction() {
        //get the values from table
        int selectedRow = manageCourseView.getCoursesJTabel().getSelectedRow();
        String courseName = (String) defaultTableModel.getValueAt(selectedRow, 1);
        manageCourseView.getTeachersjComboBox1().setSelectedItem((String) defaultTableModel.getValueAt(selectedRow, 2));

        String capacity = (String) defaultTableModel.getValueAt(selectedRow, 3);
        capacity = capacity.substring(capacity.indexOf("/") + 1);

        //set the value of tf
        manageCourseView.getCourseNameJTextField().setText(courseName);
        manageCourseView.getCapacityJTextField().setText(capacity);

        int course_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        course = courseCenterFacade.getCourse(course_id);
        manageCourseView.getCourseInfoJLabel1().setText(this.courseInfo(course));

        //gets the id
//        int teacher_id = Integer.parseInt((users.get(manageCourseView.getTeachersjComboBox1().getSelectedItem().toString())).toString());
    }

    public int addButtonAction() {
      int created = -1;
        if (valdiateEmptyFields()) {
            String courseName = manageCourseView.getCourseNameJTextField().getText();
            int capacity = Integer.parseInt(manageCourseView.getCapacityJTextField().getText());
            int teacher_id = Integer.parseInt((users.get(manageCourseView.getTeachersjComboBox1().getSelectedItem().toString())).toString());
            Course course = new Course(courseName, capacity, teacher_id);
            created= courseCenterFacade.createCourse(course);
            courseModel.setCourses(courseCenterFacade.getAllCourses());

        }
        return created;
    }

    public int updateButtonAction() {
        int updated = -1;
        int selectedRow = manageCourseView.getCoursesJTabel().getSelectedRow();
        if (valdiateEmptyFields()) {
            String courseName = manageCourseView.getCourseNameJTextField().getText();
            int capacity = Integer.parseInt(manageCourseView.getCapacityJTextField().getText());
            int teacher_id = Integer.parseInt((users.get(manageCourseView.getTeachersjComboBox1().getSelectedItem().toString())).toString());
            int course_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
            Course updateCourse = new Course(course_id, courseName, capacity, teacher_id);
            manageCourseView.getCourseInfoJLabel1().setText(this.courseInfo(updateCourse));

            updated= courseCenterFacade.updateCourse(updateCourse);
            courseModel.setCourses(courseCenterFacade.getAllCourses());

        } 
        return updated;
    }

    public int deleteButtonAction() {
        int selectedRow = manageCourseView.getCoursesJTabel().getSelectedRow();
        int course_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);

        int approved = JOptionPane.showConfirmDialog(manageCourseView, "Are you sure that u want to delete this course?");
        if (approved == JOptionPane.OK_OPTION) {
            return courseCenterFacade.deleteCourse(course_id);
        }
        return -1;
    }

    private boolean valdiateEmptyFields() {
        if (manageCourseView.getCourseNameJTextField().getText().equals("") || manageCourseView.getCapacityJTextField().getText().equals("")) {
            return false;
        }
        return true;
    }

    public void reset() {
        defaultTableModel.setRowCount(0);
    }

    public void updateTable() {
        this.reset();
        this.initTable();
    }

    public void emptyFields() {
        manageCourseView.getCourseNameJTextField().setText("");
        manageCourseView.getCapacityJTextField().setText("");
        manageCourseView.getTeachersjComboBox1().setSelectedIndex(0);

    }

    public String courseInfo(Course course) {
        String teacherName = (courseCenterFacade.getUser(course.getTeacher_id())).getName();
        String courseInfo = "";
        courseInfo += "<html>Course Title : " + course.getCourse_name() + "<br/>Teacher Name :" + teacherName
                + "<br/>Number of student :" + course.getNumOfStudents() + "<br/>Capacity :" + course.getCapacity() + "<br/>Status :" + checkCourseStatus(course);

        return courseInfo;
    }

    private String checkCourseStatus(Course course) {
        String isFull = "";
        if (course.getCapacity() == course.getNumOfStudents()) {
            isFull += "Full";

        } else {
            isFull += "Open";
        }
        return isFull;
    }

}
