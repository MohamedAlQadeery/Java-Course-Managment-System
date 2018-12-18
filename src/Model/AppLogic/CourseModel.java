/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.AppLogic;

import Model.Course;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author BaDlUcK
 */
public class CourseModel extends Observable {

    private ArrayList<Course> courses;

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
        setChanged();
        notifyObservers();
    }

}
