/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.AppLogic;

import Model.User;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author BaDlUcK
 */
public class UserModel extends Observable {
      private ArrayList<User> users;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setStudents( ArrayList<User> users) {
        this.users= users;
        setChanged();
        notifyObservers();
    }
}
