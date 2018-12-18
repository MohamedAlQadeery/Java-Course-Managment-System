/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Teacher;

import DB.CourseCenterFacade;
import Model.Course;
import Model.User;
import Views.Teacher.TeacherCoursesView;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BaDlUcK
 */
public class TeacherCoursesViewController {

    User user;
    TeacherCoursesView teacherCoursesView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    ArrayList<Course> courses = new ArrayList<Course>();
    DefaultTableModel defaultTableModel;

    public void initView() {
        initTable();
    }

    private void initTable() {
        courses = courseCenterFacade.getTeacherCourses(this.user.getId());
        for (Course course : courses) {
            this.defaultTableModel.addRow(new Object[]{course.getId(), course.getCourse_name(), this.checkCourseStatus(course)});

        }
    }

    public String courseInfo(Course course) {
        String teacherName = (courseCenterFacade.getUser(course.getTeacher_id())).getName();
        String courseInfo = "";
        courseInfo += "<html>Course Title : " + course.getCourse_name() + "<br/>Teacher Name :" + teacherName
                + "<br/>Number of student :" + course.getNumOfStudents() + "<br/>Capacity :" + course.getCapacity() + "<br/>Status :" + checkCourseStatus(course);

        return courseInfo;
    }

    public Course mouseClickedAction() {
        Course course;
        int selectedRow = teacherCoursesView.getCoursesJTabel().getSelectedRow();
        int course_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        course = courseCenterFacade.getCourse(course_id);
        teacherCoursesView.getCourseInfoJLabel().setText(this.courseInfo(course));

        return course;
    }

    public TeacherCoursesViewController(User user, TeacherCoursesView teacherCoursesView) {
        this.user = user;
        this.teacherCoursesView = teacherCoursesView;
        this.defaultTableModel = (DefaultTableModel) teacherCoursesView.getCoursesJTabel().getModel();
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
