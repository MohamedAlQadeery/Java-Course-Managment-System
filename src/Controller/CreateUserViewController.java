/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.CourseCenterFacade;
import Model.AppLogic.UserModel;
import Model.User;
import Views.CreateUserView;
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
public class CreateUserViewController {

    User user = new User();
    CreateUserView createUserView;
    CourseCenterFacade courseCenterFacade = CourseCenterFacade.getCourseCenterFacade();

    public final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void uploadImageAction() {

        JFileChooser fileChooser = new JFileChooser();
        File file;
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("images", "jpg", "gif", "png", "jpeg");
        fileChooser.setFileFilter(fileNameExtensionFilter);
        int returnVal = fileChooser.showOpenDialog(createUserView);
        if (returnVal == fileChooser.APPROVE_OPTION) {
            createUserView.getUploadJButton().setText("Change");
            file = fileChooser.getSelectedFile();
            //to set the size of the image
            ImageIcon imageIcon = new ImageIcon(file.toString()); //returns path
            Image image = imageIcon.getImage().getScaledInstance(createUserView.getImageJLabel().getWidth(), createUserView.getImageJLabel().getHeight(), Image.SCALE_SMOOTH);
            //set the image to the label
            createUserView.getImageJLabel().setIcon(new ImageIcon(image));
            this.user.setImage_path(fileChooser.getSelectedFile().getAbsolutePath());

        }

    }

    public int registerAction() throws SQLException {
        int created = -1;
        if (validateEmptyFields() && validateEmail(createUserView.getEmailJTextField().getText())) {
            this.user.setUsername(createUserView.getUsernameJTextfield().getText());
            this.user.setPassword(createUserView.getPasswordJPasswordField().getText());
            this.user.setName(createUserView.getNameJTextfield().getText());
            this.user.setEmail(createUserView.getEmailJTextField().getText());

            this.user.setUser_type((String) createUserView.getTypeJComboBox().getSelectedItem());

            created = courseCenterFacade.createUser(user);

        }
        return created;
    }

    public CreateUserViewController(CreateUserView createUserView) {
        this.createUserView = createUserView;
       
    }

    private boolean validateEmptyFields() {
        if (createUserView.getUsernameJTextfield().getText().equalsIgnoreCase("")
                || createUserView.getPasswordJPasswordField().getText().equals("")
                || createUserView.getNameJTextfield().getText().equals("")
                || createUserView.getUploadJButton().getText().equalsIgnoreCase("upload")
                || createUserView.getEmailJTextField().getText().equals("")) {
            JOptionPane.showMessageDialog(createUserView, "Error empty fields");
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
        JOptionPane.showMessageDialog(createUserView, "Error in email format");
        return false;
    }

    public void emptyFields() {
        createUserView.getUsernameJTextfield().setText("");
        createUserView.getPasswordJPasswordField().setText("");
        createUserView.getNameJTextfield().setText("");
        createUserView.getEmailJTextField().setText("");
        createUserView.getImageJLabel().setIcon(null);
        createUserView.getUploadJButton().setText("Upload");

    }
}
