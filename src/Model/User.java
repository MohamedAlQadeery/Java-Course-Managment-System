/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.AppLogic.UserTypePay;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author BaDlUcK
 */
public  class User{

    protected int id; // 1 = teacher , 2 = student
    protected String username,password,name,email;
    protected BufferedImage user_image;
    protected String image_path,user_type;
    protected double grade;

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
    
    
    protected ArrayList<Course> courses ;

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public User() {
    }

    public User(int id, String username, String password, String name, String email,String image_path, BufferedImage user_image, String user_type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.image_path=image_path;
        this.user_image = user_image;
        this.user_type = user_type;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
   

    public BufferedImage getUser_image() {
        return user_image;
    }

    public void setUser_image(BufferedImage user_image) {
        this.user_image = user_image;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

   
    public String userInfo(){
        String userInfo = "";
        String coursesTitles ="";
        for(Course course : this.courses){
            coursesTitles+="-"+course.getCourse_name()+"<br/>";
        }
        userInfo+="<html>Name : "+this.getName()+"<br/>Username :"+this.getUsername()+
                "<br/>Email :"+this.getEmail()+"<br/>Type :"+this.getUser_type()+"<br/>Courses :<br/><hr>"+coursesTitles;
        
        return userInfo;
    }
    
    
    public void initUser(int id, String username, String password, String name, String email,String image_path, BufferedImage user_image, String user_type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.image_path=image_path;
        this.user_image = user_image;
        this.user_type = user_type;
    }

    
    
   
    
    

    
}
