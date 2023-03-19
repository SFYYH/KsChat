package cn.com.server.db;


import cn.com.pojo.Message;
import cn.com.pojo.User;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DB implements Serializable {
    private static final long serialVersionUID = 123412452352352L;
    private  HashMap<String, User> userData ;
    private  HashMap<String, ObjectOutputStream> userOut ;
    private  HashMap<String, ArrayList<Message>>  offlineMsg ;

    public DB() {

    }

    public void setUserData(HashMap<String, User> userData) {
        this.userData = userData;
    }

    public void setUserOut(HashMap<String, ObjectOutputStream> userOut) {
        this.userOut = userOut;
    }

    public void setOfflineMsg(HashMap<String, ArrayList<Message>> offlineMsg) {
        this.offlineMsg = offlineMsg;
    }

    public  HashMap<String, User> getUserData() {
        return userData;
    }
    public  HashMap<String, ArrayList<Message>> getOfflineMsg (){
        return offlineMsg;
    }
    public  HashMap<String, ObjectOutputStream> getUserOut() {
        return userOut;
    }
}
