/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Student;

import DB.CourseCenterFacade;
import Model.Course;
import Model.User;
import Views.Student.StudentCourseView;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BaDlUcK
 */
public class StudentCourseViewController {

    User user;
    StudentCourseView studentCourseView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    ArrayList<Course> myCourses = new ArrayList<Course>();
    DefaultTableModel defaultTableModel;

    public StudentCourseViewController(User user, StudentCourseView studentCourseView) {
        this.user = user;
        this.studentCourseView = studentCourseView;
        defaultTableModel = (DefaultTableModel) this.studentCourseView.getCoursesJTabel().getModel();
    }

    public void myCourseAction() {
        if (studentCourseView.getMyCoursesJButton().getText().equalsIgnoreCase("My Courses")) {
            defaultTableModel.setRowCount(0);
            myCourses = courseCenterFacade.getStudentCourses(user);
            for (Course course : myCourses) {
                defaultTableModel.addRow(new Object[]{course.getId(), course.getCourse_name(), checkCourseStatus(course)});
            }
            studentCourseView.getMyCoursesJButton().setText("Hide My Courses");
            studentCourseView.getEnrollJButton().setEnabled(false);

        } else if (studentCourseView.getMyCoursesJButton().getText().equalsIgnoreCase("Hide My Courses")) {
            studentCourseView.getEnrollJButton().setEnabled(true);
            defaultTableModel.setRowCount(0);
            studentCourseView.getMyCoursesJButton().setText("My Courses");

        }

    }

    public void avaCoursesAction() {
        if (studentCourseView.getAvaCoursesJButton().getText().equalsIgnoreCase("Avaliable Courses")) {
            defaultTableModel.setRowCount(0);
            studentCourseView.getAvaCoursesJButton().setText("Hide Avaliable Courses");
            myCourses = courseCenterFacade.getAvaliableCourses(user);
            for (Course course : myCourses) {
                defaultTableModel.addRow(new Object[]{course.getId(), course.getCourse_name(), checkCourseStatus(course)});
            }
            studentCourseView.getUnEnrollJButton().setEnabled(false);
        } else if (studentCourseView.getAvaCoursesJButton().getText().equalsIgnoreCase("Hide Avaliable Courses")) {
            studentCourseView.getUnEnrollJButton().setEnabled(true);
            defaultTableModel.setRowCount(0);
            studentCourseView.getAvaCoursesJButton().setText("Avaliable Courses");

        }
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

    public Course mouseClickedAction() {
        Course course;
        int selectedRow = studentCourseView.getCoursesJTabel().getSelectedRow();
        int course_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        course = courseCenterFacade.getCourse(course_id);
        studentCourseView.getCourseInfoJLabel().setText(this.courseInfo(course));

        return course;
    }

    private String courseInfo(Course course) {
        String teacherName = (courseCenterFacade.getUser(course.getTeacher_id())).getName();
        String courseInfo = "";
        courseInfo += "<html>Course Title : " + course.getCourse_name() + "<br/>Teacher Name :" + teacherName
                + "<br/>Status :" + checkCourseStatus(course);

        return courseInfo;
    }

    public int enrollAction() {
        int enrolled = -1;
        int selectedRow = studentCourseView.getCoursesJTabel().getSelectedRow();
        if (checkSelectedRow(selectedRow) == false) {
            JOptionPane.showMessageDialog(studentCourseView, "please select a course");
            return enrolled;
        }
        int course_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        Course course = courseCenterFacade.getCourse(course_id);
        if (course.getNumOfStudents() != course.getCapacity()) {
            enrolled = courseCenterFacade.registerStudentCourse(user, course);
            if (enrolled != -1) {
                courseCenterFacade.increaseNumOfStudents(course);
            }
        } else {
            JOptionPane.showMessageDialog(studentCourseView, "Sorry the course is full");
        }
        return enrolled;
    }

   

    public int unEnrollAction() {
        int unEnrolled = -1;
        int selectedRow = studentCourseView.getCoursesJTabel().getSelectedRow();
        System.out.println(selectedRow);
        if (checkSelectedRow(selectedRow) == false) {
            JOptionPane.showMessageDialog(studentCourseView, "please select a course");
            return unEnrolled;
        }
        int course_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        Course course = courseCenterFacade.getCourse(course_id);
        int approved = JOptionPane.showConfirmDialog(studentCourseView, "Are you sure you want to unenroll from this course ? ");
        if (approved == JOptionPane.OK_OPTION) {
            unEnrolled = courseCenterFacade.deleteStudentCourse(user.getId(), course);
            if (unEnrolled != -1) {
                courseCenterFacade.dcreaseNumOfStudents(course);
            }
        }
        return unEnrolled;
    }

    public void refreshMyCourseTable() {
        defaultTableModel.setRowCount(0);
        myCourses = courseCenterFacade.getStudentCourses(user);
        for (Course course : myCourses) {
            defaultTableModel.addRow(new Object[]{course.getId(), course.getCourse_name(), checkCourseStatus(course)});
        }
        studentCourseView.getMyCoursesJButton().setText("Hide My Courses");

    }

    public void refreshAvaCourseTable() {
        defaultTableModel.setRowCount(0);
        studentCourseView.getAvaCoursesJButton().setText("Hide Avaliable Courses");
        myCourses = courseCenterFacade.getAvaliableCourses(user);
        for (Course course : myCourses) {
            defaultTableModel.addRow(new Object[]{course.getId(), course.getCourse_name(), checkCourseStatus(course)});
        }

    }
    
     private boolean checkSelectedRow(int selectedRow) {
        if (selectedRow == -1) {
            return false;
        }
        return true;

    }

}
