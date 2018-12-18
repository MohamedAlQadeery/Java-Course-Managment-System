/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Teacher;

import DB.CourseCenterFacade;
import Model.User;
import Views.Teacher.UpdateTeacherView;
import java.awt.Image;
import java.io.File;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author BaDlUcK
 */
public class UpdateTeacherViewController {

    User user;
    UpdateTeacherView updateTeacherView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    
    public final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    public void initView() {
        setImageLabel();
        initUpdateForm();
    }

    public void uploadImageAction() {
        
        JFileChooser fileChooser = new JFileChooser();
        File file;
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("images", "jpg", "gif", "png", "jpeg");
        fileChooser.setFileFilter(fileNameExtensionFilter);
        int returnVal = fileChooser.showOpenDialog(updateTeacherView);
        if (returnVal == fileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            //to set the size of the image
            ImageIcon imageIcon = new ImageIcon(file.toString()); //returns path
            Image image = imageIcon.getImage().getScaledInstance(updateTeacherView.getImageJLabel().getWidth(), updateTeacherView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
            //set the image to the label
            updateTeacherView.getImageJLabel().setIcon(new ImageIcon(image));
            this.user.setImage_path(fileChooser.getSelectedFile().getAbsolutePath());
            
        }
        
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public UpdateTeacherViewController(User user, UpdateTeacherView updateTeacherView) {
        this.user = user;
        this.updateTeacherView = updateTeacherView;
    }

    private void setImageLabel() {
        ImageIcon imageIcon = new ImageIcon(user.getUser_image());
        Image image = imageIcon.getImage().getScaledInstance(updateTeacherView.getImageJLabel().getWidth(), updateTeacherView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
        updateTeacherView.getImageJLabel().setIcon(new ImageIcon(image));
        
    }
    
    private void initUpdateForm() {
        updateTeacherView.getUsernameJTextfield().setText(user.getUsername());
        updateTeacherView.getEmailJTextField().setText(user.getEmail());
        
    }
    
    public int updateAction() throws SQLException {
        int created = -1;
        if (validateEmptyFields() && validateEmail(updateTeacherView.getEmailJTextField().getText())) {
            this.user.setUsername(updateTeacherView.getUsernameJTextfield().getText());
            this.user.setPassword(updateTeacherView.getPasswordJPasswordField().getText());
            this.user.setEmail(updateTeacherView.getEmailJTextField().getText());
            
            created = courseCenterFacade.updateUser(user);
            
        }
        return created;
    }
    
    public void setUserImage() {
        this.user.setUser_image(courseCenterFacade.getUser(this.user.getId()).getUser_image());
    }
    
    private boolean validateEmptyFields() {
        if (updateTeacherView.getUsernameJTextfield().getText().equalsIgnoreCase("")
                || updateTeacherView.getPasswordJPasswordField().getText().equals("")
                || updateTeacherView.getEmailJTextField().getText().equals("")) {
            JOptionPane.showMessageDialog(updateTeacherView, "Error empty fields");
            return false;
        }
        
        return true;
        
    }
    
    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        boolean correct = matcher.find();
        if (correct) {
            return true;
            
        }
        JOptionPane.showMessageDialog(updateTeacherView, "Error in email format");
        return false;
    }
    
}
