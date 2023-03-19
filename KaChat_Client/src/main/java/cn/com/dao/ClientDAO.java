package cn.com.dao;


import cn.com.db.ClientDB;
import cn.com.pojo.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDAO {


    public static List<User>  getFList(){
        return ClientDB.clientUser.getFriends();
    }
    public static void  upData (User user){
        ClientDB.clientUser = user;
    }

    public static User getFriend(String uID){
        for (User u :ClientDB.clientUser.getFriends()){
            if (u.getID().equals(uID)){
                return u;
            }
        }
        return null;
    }
    public static User getUser() {
        return ClientDB.clientUser;
    }

    public static DefaultListModel<User>  searchFList(String searchID){
        DefaultListModel<User> friendsList = new DefaultListModel<User>();
        ArrayList<User> friends = ClientDB.clientUser.getFriends();
        for (User u : friends){
            if (u.getID().indexOf(searchID)!=-1){
                friendsList.addElement(u);
            }
        }
        return friendsList;

    }

}
