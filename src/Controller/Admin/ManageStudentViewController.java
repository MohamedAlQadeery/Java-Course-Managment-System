/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import DB.CourseCenterFacade;
import Model.User;
import Views.Admin.ManageStudentView;
import Views.Admin.ManageTeacherView;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BaDlUcK
 */
public class ManageStudentViewController {

    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    ManageStudentView manageStudentView;
    User user;
    DefaultTableModel defaultTableModel;

    public void initView() {
        initTable();
    }

    public void initTable() {
        ArrayList<User> students = courseCenterFacade.getAllUsers("student");
        for (User student : students) {
            defaultTableModel.addRow(new Object[]{student.getId(), student.getName(), student.getUsername(), student.getEmail(), student.getUser_image()});
        }
    }

    public void onClickAction() {

        int selectedRow = this.manageStudentView.getStudentsJTabel().getSelectedRow();
        ImageIcon imageIcon = new ImageIcon((BufferedImage) defaultTableModel.getValueAt(selectedRow, 4));
        Image image = imageIcon.getImage().getScaledInstance(this.manageStudentView.getImageJLabel().getWidth(),
                this.manageStudentView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
        this.manageStudentView.getImageJLabel().setIcon(new ImageIcon(image));
      
        int user_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
        User selectedUser = courseCenterFacade.getUser(user_id);
        selectedUser.setCourses(courseCenterFacade.getStudentCourses(selectedUser));
        this.manageStudentView.getStudentInfoJLabel().setText(selectedUser.userInfo());

    }

    public void keyPressedAction() {
        ArrayList<User> students = courseCenterFacade.searchUser("student", this.manageStudentView.getSearchStudentJTextField().getText());
        for (User student : students) {
            defaultTableModel.addRow(new Object[]{student.getId(), student.getName(), student.getUsername(), student.getEmail(), student.getUser_image()});
        }

    }

    public User updateButtonAction() {

        int selectedRow = manageStudentView.getStudentsJTabel().getSelectedRow();
        if (selectedRow != -1) {
            int user_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
            return courseCenterFacade.getUser(user_id);
        }
        return null;
    }

    public void deleteButtonActtion() {
        int selectedRow = manageStudentView.getStudentsJTabel().getSelectedRow();
        if (selectedRow != -1) {
            int user_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
            int confirmed = JOptionPane.showConfirmDialog(manageStudentView, "Are you sure that you want to delete the student ?");
            if (confirmed == JOptionPane.OK_OPTION) {
                courseCenterFacade.deleteUser(user_id);
                JOptionPane.showMessageDialog(manageStudentView, "Done the student has been deleted successfully");
                manageStudentView.getImageJLabel().setIcon(null);
            }
        } else {
            JOptionPane.showMessageDialog(manageStudentView, "Please select a student to delete");
        }

    }

    public ManageStudentViewController(ManageStudentView manageStudentView) {
        this.manageStudentView = manageStudentView;
        defaultTableModel = (DefaultTableModel) manageStudentView.getStudentsJTabel().getModel();

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void reset() {
        defaultTableModel.setRowCount(0);

    }

}
