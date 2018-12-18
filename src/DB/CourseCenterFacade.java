/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.AppFactory;
import Model.Course;
import Model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author BaDlUcK
 */
public class CourseCenterFacade {

    private static CourseCenterFacade courseCenterFacade= null;
    private DbUser dbUser;
    private DbCourse dbCourse;
    private DbLogin dbLogin;
    private AppFactory appFactory ;


    private CourseCenterFacade() {
        dbUser = new DbUser();
        dbCourse = new DbCourse();
        appFactory = AppFactory.getAppFactory();

    }
    
    public User factoryCreateUser(){
        return appFactory.createUser();
    }
    
    public Course factoryCreateCourse(){
        return appFactory.createCourse();
    }
    public static CourseCenterFacade getCourseCenterFacade() {
        if (courseCenterFacade == null) {
            courseCenterFacade = new CourseCenterFacade();
        }
        return courseCenterFacade;
    }

    public User AppLogin(String username, String Password) {
        User user = dbUser.login(username, Password);
        if (user != null) {
            return user;
        }
        return null;
    }

    public int createUser(User user) throws SQLException {
        return dbUser.create_user(user);

    }

    public ArrayList<User> getAllUsers(String type) {
        ArrayList<User> teachers = dbUser.get_all_teachers(type);
        return teachers;
    }

    public ArrayList<User> searchUser(String type,String name) {
        ArrayList<User> users = dbUser.search_user(type,name);
        return users;
    }

    public int updateUser(User updateUser) {
        return dbUser.update_user(updateUser);
    }

    public User getUser(int id) {
        return dbUser.getUser(id);
    }

    public int deleteUser(int id) {
        return dbUser.delete_user(id);
    }
    
    public int getCountUsers(){
        return dbUser.countUsers();
    }
    
  
    
    public int createCourse(Course course){
        return dbCourse.create_course(course);
    }
    
    public Course getCourse(int id){
        return dbCourse.get_course(id);
    }
    public int updateCourse (Course course){
        return dbCourse.update_course(course);
    }

    public int deleteCourse(int id){
        return dbCourse.delete_course(id);
    }
   
    public ArrayList<Course> getAllCourses(){
        return dbCourse.getAllCourses();
    }
    public ArrayList<Course> getTeacherCourses(int teacher_id){
        return dbCourse.getTeacherCourses(teacher_id);
    }
    
    public HashMap<String,Integer> getUsersHashMap(String type){
        return dbUser.getUsersHashMap(type);
    }
    
    public int getCourseCount(){
        return dbCourse.get_count_courses();
    }
    
    public ArrayList<User> getCourseStudents(Course course){
        return dbCourse.getCourseStudents(course);
    }
    public int registerStudentCourse(User user , Course course){
      return  dbCourse.register_student_course(user, course);
    }
    
    public int setStudentGrade(int student_id , Course course,double grade){
        return dbCourse.set_student_grade(student_id, course, grade);
    }
    
    public int deleteStudentCourse(int student_id,Course course){
        return dbCourse.delete_student_course(student_id, course);
    }
    
    public ArrayList<Course> getStudentCourses(User user){
        return dbCourse.get_student_courses(user);
    }
    public HashMap<String,Double> getStudentCoursesWithGrades(User user){
        return dbCourse.getHashMapStudentCourses(user);
    }
    
    public ArrayList<Course> getAvaliableCourses(User user){
        return dbCourse.get_avaliable_courses(user);
    }
    
    public int increaseNumOfStudents(Course course){
        return dbCourse.inc_num_of_students(course);
    }
    
    public int dcreaseNumOfStudents(Course course){
        return dbCourse.dec_num_of_students(course);
    }
}
