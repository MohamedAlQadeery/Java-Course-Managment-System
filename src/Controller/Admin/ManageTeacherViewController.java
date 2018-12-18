/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import DB.CourseCenterFacade;
import Model.User;
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
public class ManageTeacherViewController {

    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    ManageTeacherView manageTeacherView;
    User user;
    DefaultTableModel defaultTableModel;

    public void initView() {
        initTable();
    }

    public void initTable() {
        ArrayList<User> teachers = courseCenterFacade.getAllUsers("teacher");
        for (User teacher : teachers) {
            defaultTableModel.addRow(new Object[]{teacher.getId(), teacher.getName(), teacher.getUsername(), teacher.getEmail(), teacher.getUser_image()});
        }
    }

    public void onClickAction() {

        int selectedRow = this.manageTeacherView.getTeachersJTable().getSelectedRow();
        ImageIcon imageIcon = new ImageIcon((BufferedImage) defaultTableModel.getValueAt(selectedRow, 4));
        Image image = imageIcon.getImage().getScaledInstance(this.manageTeacherView.getImageJLabel().getWidth(),
                this.manageTeacherView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
        this.manageTeacherView.getImageJLabel().setIcon(new ImageIcon(image));
        int user_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
        User selectedUser = courseCenterFacade.getUser(user_id);
        selectedUser.setCourses(courseCenterFacade.getTeacherCourses(selectedUser.getId()));

        this.manageTeacherView.getTeacherInfoJLabel().setText(selectedUser.userInfo());

    }

    public void keyPressedAction() {
        ArrayList<User> teachers = courseCenterFacade.searchUser("teacher", this.manageTeacherView.getSearchTeacherNameJTextField().getText());
        for (User teacher : teachers) {
            defaultTableModel.addRow(new Object[]{teacher.getId(), teacher.getName(), teacher.getUsername(), teacher.getEmail(), teacher.getUser_image()});
        }

    }

    public User updateButtonAction() {

        int selectedRow = manageTeacherView.getTeachersJTable().getSelectedRow();
        if (selectedRow != -1) {
            int user_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
            return courseCenterFacade.getUser(user_id);
        }
        return null;
    }

    public void deleteButtonActtion() {
        int selectedRow = manageTeacherView.getTeachersJTable().getSelectedRow();
        if (selectedRow != -1) {
            int user_id = (Integer) defaultTableModel.getValueAt(selectedRow, 0);
            int confirmed = JOptionPane.showConfirmDialog(manageTeacherView, "Are you sure that you want to delete the teacher ?");
            if (confirmed == JOptionPane.OK_OPTION) {
                courseCenterFacade.deleteUser(user_id);
                JOptionPane.showMessageDialog(manageTeacherView, "Done the teacher has been deleted successfully");
                manageTeacherView.getImageJLabel().setIcon(null);

            }
        } else {
            JOptionPane.showMessageDialog(manageTeacherView, "Please select a teacher to delete");
        }

    }

    public ManageTeacherViewController(ManageTeacherView manageTeacherView) {
        this.manageTeacherView = manageTeacherView;
        defaultTableModel = (DefaultTableModel) manageTeacherView.getTeachersJTable().getModel();

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
