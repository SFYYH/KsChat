package cn.com.pojo;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 2L;
    private String ID;
    private String name;
    private String password;
    private ImageIcon userIcon;
    private String motto;
    private boolean online;
    private ArrayList<User> friends;

    public User(){
        friends = new ArrayList<>();
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }
    public ArrayList<User> getFriends() {
        return friends;
    }



    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getPortrait() {
        return userIcon;
    }

    public void setPortrait(ImageIcon portrait) {
        this.userIcon = new ImageIcon(portrait.getImage()){
            private static final long serialVersionUID = -962022720109015502L;
        };

    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID.equals(user.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }


}
