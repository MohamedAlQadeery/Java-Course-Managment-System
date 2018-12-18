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
public class AppFactory {

    private static AppFactory appFactory = null;

    private AppFactory() {

    }

   
 
    public static AppFactory getAppFactory() {
        if (appFactory == null) {
            appFactory = new AppFactory();
        } 
        return appFactory;
    }

   
    public User createUser() {
        return new User();
    }

    public Course createCourse() {
        return new Course();
    }

}
