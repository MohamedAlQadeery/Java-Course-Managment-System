/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Teacher;

import DB.CourseCenterFacade;
import Model.Course;
import Model.User;
import Views.Teacher.ManageCourseView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BaDlUcK
 */
public class ManageCourseViewController {

    Course course;
    ManageCourseView manageCourseView;
    DefaultTableModel defaultTableModel;
    ArrayList<User> courseStudents = new ArrayList<>();
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();

    public void initView() {
        setTitleLabel();
        initTable();
        setKeyListenerForTextFields();
    }

    private void setTitleLabel() {
        manageCourseView.getTitleJLabel().setText("Manage " + course.getCourse_name() + " Course Page");
    }

    public void initTable() {
        courseStudents = courseCenterFacade.getCourseStudents(course);
        for (User courseStudent : courseStudents) {
            defaultTableModel.addRow(new Object[]{courseStudent.getId(), courseStudent.getName(), courseStudent.getGrade()});
        }
    }

    public int mouseClickedAction() {
        int selectedRow = manageCourseView.getStudentsJTable().getSelectedRow();
        String selectedStudentName = (String) defaultTableModel.getValueAt(selectedRow, 1);
        manageCourseView.getSelectedStudentNameJLabel().setText("<html>Selected Student Name :<br>" + selectedStudentName + "</html>");
        return (int) defaultTableModel.getValueAt(selectedRow, 0);

    }

    public int setGradeButtonAction() {
        double midGrade = 0, finalExamGrade = 0, finalResult = 0;
        int selectedRow = manageCourseView.getStudentsJTable().getSelectedRow();
        int student_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        if (checkEmptyFields()) {
            midGrade = Double.parseDouble(manageCourseView.getMidtermGradeJTextField().getText());
            finalExamGrade = Double.parseDouble(manageCourseView.getFinalGradeJTextField().getText());
            finalResult = midGrade * 50 / 100 + finalExamGrade * 50 / 100;
            return courseCenterFacade.setStudentGrade(student_id, course, finalResult);

        } else {
            JOptionPane.showMessageDialog(manageCourseView, "Please insert the grades");
            return -1;
        }
    }

    public int deleteButtonAction() {
       int deleted= -1;
        int selectedRow = manageCourseView.getStudentsJTable().getSelectedRow();
        int student_id = (int) defaultTableModel.getValueAt(selectedRow, 0);
        int approved = JOptionPane.showConfirmDialog(manageCourseView, "Are you sure that you want to delete the student ?");
        if (approved == JOptionPane.OK_OPTION) {
             deleted = courseCenterFacade.deleteStudentCourse(student_id, course);
            if (deleted != -1) {
                courseCenterFacade.dcreaseNumOfStudents(course);
            }
        } 
        return deleted;
    }

    public ManageCourseViewController(Course course, ManageCourseView manageCourseView) {
        this.course = course;
        this.manageCourseView = manageCourseView;
        defaultTableModel = (DefaultTableModel) this.manageCourseView.getStudentsJTable().getModel();
    }

    private boolean checkEmptyFields() {
        if (manageCourseView.getMidtermGradeJTextField().equals("") || manageCourseView.getFinalGradeJTextField().equals("")) {
            return false;
        }
        return true;
    }

    private void setKeyListenerForTextFields() {
        manageCourseView.getMidtermGradeJTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9')
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });

        manageCourseView.getFinalGradeJTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9')
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
    }

    public void clear() {
        manageCourseView.getSelectedStudentNameJLabel().setText("");
        manageCourseView.getMidtermGradeJTextField().setText("");
        manageCourseView.getFinalGradeJTextField().setText("");
    }

    public void updateTable() {
        defaultTableModel.setRowCount(0);
        this.initTable();
    }
}
