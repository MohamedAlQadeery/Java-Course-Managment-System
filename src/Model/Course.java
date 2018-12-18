/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author BaDlUcK
 */
public class Course {
    protected int id,teacher_id;
    protected String course_name;
    protected int capacity;
    protected int numOfStudents;

    public Course(){}
    
   public Course(String name,int capacity,int teacherId){
       this.course_name=name;
       this.capacity=capacity;
       this.teacher_id=teacherId;
   }

   public Course(int id,String name,int num_of_students,int capacity , int teacher_id){
        this.id=id;
        this.course_name=name;
        this.capacity = capacity;
        this.teacher_id=teacher_id;
        this.numOfStudents=num_of_students;
    }
   

    public int getNumOfStudents() {
        return numOfStudents;
    }
    
    public Course(int id,String name,int capacity , int teacher_id){
        this.id=id;
        this.course_name=name;
        this.capacity = capacity;
        this.teacher_id=teacher_id;
        this.numOfStudents=0;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

  
   
    
        
}
