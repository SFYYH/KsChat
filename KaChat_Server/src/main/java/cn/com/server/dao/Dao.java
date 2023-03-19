package cn.com.server.dao;

import cn.com.pojo.Message;
import cn.com.pojo.User;
import cn.com.server.db.DB;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Dao {
    private static DB database ;


    public Dao(){
        database = new DB();
        AutoBackup autoBackup = new AutoBackup(5000,new File("/userData"),database) ;
        autoBackup.start();
    }

    public  void addUser (User u){
        String uID = u.getID();
        database.getUserData().put(uID,u);
    }

    public void delUser (String uID){
        database.getUserData().remove(uID);
    }

    public  User findUser (String uID){

        return  database.getUserData().get(uID);
    }

    public  boolean containsUID (String uID){
        return  database.getUserData().containsKey(uID);
    }

    public  int userCount() {
        return database.getUserData().size();
    }
    public  ObjectOutputStream getUserOut(String uID){
        return database.getUserOut().get(uID);
    }

    public  HashMap<String, ObjectOutputStream> getUserOutMap(){
        return database.getUserOut();
    }

    public  void addUserOut(String uID,ObjectOutputStream o){
        database.getUserOut().put(uID,o);
    }

    protected static DB getDatabase(){
        return database;
    }


    public  ArrayList<Message> getOfflineMsg(String uID) {
        HashMap<String, ArrayList<Message>> offlineMsg = database.getOfflineMsg();
        if (!offlineMsg.containsKey(uID)){
            offlineMsg.put(uID,new ArrayList<Message>());
        }
        return database.getOfflineMsg().get(uID);
    }


    public void adduser() {
        ImageIcon imageIcon = new ImageIcon(getResource("/usericon.png","usericon",".png").getAbsolutePath());
        User clientUser1 = new User();
        User clientUser2  = new User();
        User clientUser3 = new User();
        clientUser1.setOnline(true);
        clientUser1.setID("837080904");
        clientUser1.setPortrait(imageIcon);
        clientUser1.setName("卡莫SAMA");
        clientUser1.setPassword("123");

        clientUser1.getFriends().add(clientUser2);
        clientUser1.getFriends().add(clientUser3);
        clientUser2.setOnline(false);
        clientUser2.setID("1366502244");
        clientUser2.setPortrait(imageIcon);
        clientUser2.setName("珝宝");
        clientUser2.setPassword("123");
        clientUser2.getFriends().add(clientUser1);

        clientUser3.setOnline(false);
        clientUser3.setID("123456");
        clientUser3.setPortrait(imageIcon);
        clientUser3.setName("哈哈");
        clientUser3.setPassword("123");

        clientUser3.getFriends().add(clientUser1);
        database.getUserData().put(clientUser1.getID(),clientUser1);
        database.getUserData().put(clientUser2.getID(),clientUser2);
        database.getUserData().put(clientUser3.getID(),clientUser3);
    }
    public static File getResource(String path, String prefix, String suffix) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        InputStream is = null;
        try {
            File f = File.createTempFile(prefix, suffix);
            f.deleteOnExit();
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int length;
            byte[] bytes = new byte[1024];
            is = Dao.class.getResourceAsStream(path);
//            bis = new BufferedInputStream(ResourcesUtils.class.getResourceAsStream(path));
            while ((length = is.read(bytes)) > 0) {
                bos.write(bytes, 0, length);
                bos.flush();
            }
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
