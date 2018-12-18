///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Model;
//
//import Model.AppLogic.UserTypePay;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//
///**
// *
// * @author BaDlUcK
// */
//public class Teacher extends User implements UserTypePay{
//
//    private ArrayList<Course> courses;
//
//    public Teacher(ArrayList<Course> courses, int id, String username, String password, String name, String email, BufferedImage user_image, String user_type) {
//        super(id, username, password, name, email, user_image, user_type);
//        this.courses = courses;
//    }
//
//    public Teacher(int id, String username, String password, String name, String email, BufferedImage user_image, String user_type) {
//        super(id, username, password, name, email, user_image, user_type);
//    }
//
//    
//    
//   
//
//    public ArrayList<Course> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(ArrayList<Course> courses) {
//        this.courses = courses;
//    }
//
//    public Teacher() {
//    }
//
//    @Override
//    public double pay(Course c) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    
//   
//
//}
