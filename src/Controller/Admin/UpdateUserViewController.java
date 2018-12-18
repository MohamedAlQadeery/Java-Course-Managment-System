/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import DB.CourseCenterFacade;
import Model.User;
import Views.Admin.UpdateUserView;
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
public class UpdateUserViewController {

    User user;
    UpdateUserView updateUserView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();
    
      public final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public UpdateUserViewController(User user, UpdateUserView updateUserView) {
        this.user = user;
        this.updateUserView = updateUserView;
    }

   
    public void initView(){
        setImageLabel();
        initUpdateForm();
    }
    public void uploadImageAction() {

        JFileChooser fileChooser = new JFileChooser();
        File file;
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("images", "jpg", "gif", "png", "jpeg");
        fileChooser.setFileFilter(fileNameExtensionFilter);
        int returnVal = fileChooser.showOpenDialog(updateUserView);
        if (returnVal == fileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            //to set the size of the image
            ImageIcon imageIcon = new ImageIcon(file.toString()); //returns path
            Image image = imageIcon.getImage().getScaledInstance(updateUserView.getImageJLabel().getWidth(), updateUserView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
            //set the image to the label
            updateUserView.getImageJLabel().setIcon(new ImageIcon(image));
            this.user.setImage_path(fileChooser.getSelectedFile().getAbsolutePath());

        }

    }
    private void setImageLabel() {
        ImageIcon imageIcon = new ImageIcon(user.getUser_image());
        Image image = imageIcon.getImage().getScaledInstance(updateUserView.getImageJLabel().getWidth(), updateUserView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
        updateUserView.getImageJLabel().setIcon(new ImageIcon(image));

    }

    private void initUpdateForm() {
        updateUserView.getUsernameJTextfield().setText(user.getUsername());
        updateUserView.getPasswordJPasswordField().setText(user.getPassword());
        updateUserView.getNameJTextfield().setText(user.getName());
        updateUserView.getEmailJTextField().setText(user.getEmail());
        updateUserView.getTypeJComboBox().setSelectedItem(user.getUser_type());

    }
    
    public int updateAction() throws SQLException {
        int created = -1;
        if (validateEmptyFields() && validateEmail(updateUserView.getEmailJTextField().getText())) {
            this.user.setUsername(updateUserView.getUsernameJTextfield().getText());
            this.user.setPassword(updateUserView.getPasswordJPasswordField().getText());
            this.user.setName(updateUserView.getNameJTextfield().getText());
            this.user.setEmail(updateUserView.getEmailJTextField().getText());
          
            this.user.setUser_type((String) updateUserView.getTypeJComboBox().getSelectedItem());

            created = courseCenterFacade.updateUser(user);

        }
        return created;
    }
    
    private boolean validateEmptyFields() {
        if (updateUserView.getUsernameJTextfield().getText().equalsIgnoreCase("")
                || updateUserView.getPasswordJPasswordField().getText().equals("")
                || updateUserView.getNameJTextfield().getText().equals("")
                || updateUserView.getUploadJButton().getText().equalsIgnoreCase("upload")
                || updateUserView.getEmailJTextField().getText().equals("")) {
            JOptionPane.showMessageDialog(updateUserView, "Error empty fields");
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
        JOptionPane.showMessageDialog(updateUserView,"Error in email format");
        return false;
    }
    
    
    

}
