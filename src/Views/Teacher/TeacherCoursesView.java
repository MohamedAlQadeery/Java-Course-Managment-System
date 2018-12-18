/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Teacher;

import Views.Admin.*;
import Controller.Admin.ManageCourseViewController;
import Controller.Teacher.TeacherCoursesViewController;
import Model.Course;
import Model.User;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author BaDlUcK
 */
public class TeacherCoursesView extends javax.swing.JFrame {

    User authUser;
    Course course;
    TeacherCoursesViewController teacherCoursesViewController;

    public JLabel getCourseInfoJLabel() {
        return courseInfoJLabel;
    }

    public void setCourseInfoJLabel(JLabel courseInfoJLabel) {
        this.courseInfoJLabel = courseInfoJLabel;
    }

    public JTable getCoursesJTabel() {
        return coursesJTabel;
    }

    public void setCoursesJTabel(JTable coursesJTabel) {
        this.coursesJTabel = coursesJTabel;
    }

    public TeacherCoursesView(User user) {
        initComponents();
        setLocationRelativeTo(null);
        this.authUser = user;
        teacherCoursesViewController = new TeacherCoursesViewController(user, this);
        teacherCoursesViewController.initView();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleJLabel = new javax.swing.JLabel();
        backJButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        coursesJTabel = new javax.swing.JTable();
        courseInfoJLabel = new javax.swing.JLabel();
        manageCourseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleJLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        titleJLabel.setText("Manage Teacher Courses");

        backJButton.setText("Back");
        backJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backJButtonActionPerformed(evt);
            }
        });

        coursesJTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Course Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        coursesJTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                coursesJTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(coursesJTabel);
        if (coursesJTabel.getColumnModel().getColumnCount() > 0) {
            coursesJTabel.getColumnModel().getColumn(0).setMinWidth(0);
            coursesJTabel.getColumnModel().getColumn(0).setMaxWidth(0);
            coursesJTabel.getColumnModel().getColumn(1).setResizable(false);
            coursesJTabel.getColumnModel().getColumn(2).setResizable(false);
        }

        courseInfoJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        courseInfoJLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        manageCourseButton.setText("Manage Course");
        manageCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageCourseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backJButton)
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(courseInfoJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(manageCourseButton)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(courseInfoJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(manageCourseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void coursesJTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coursesJTabelMouseClicked
        // TODO add your handling code here:
        this.course = teacherCoursesViewController.mouseClickedAction();
    }//GEN-LAST:event_coursesJTabelMouseClicked

    private void backJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backJButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new TeacherHomeView(authUser).setVisible(true);
    }//GEN-LAST:event_backJButtonActionPerformed

    private void manageCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageCourseButtonActionPerformed
        // TODO add your handling code here:
        if (this.course != null) {
            this.dispose();
            new ManageCourseView(authUser, course).setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Please select a course");
        }
    }//GEN-LAST:event_manageCourseButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TeacherCoursesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeacherCoursesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeacherCoursesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeacherCoursesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backJButton;
    private javax.swing.JLabel courseInfoJLabel;
    private javax.swing.JTable coursesJTabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton manageCourseButton;
    private javax.swing.JLabel titleJLabel;
    // End of variables declaration//GEN-END:variables
}