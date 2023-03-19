package cn.com.server.action;


import cn.com.pojo.Message;
import cn.com.pojo.MessageType;
import cn.com.pojo.User;
import cn.com.server.dao.Dao;
import cn.com.server.util.ServerUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Action {

    private ServerSocket serverSocket;
    private Dao dao;
    public static Action action;
    public Action(){
        dao = new Dao();
        action = this;
        try {
            serverSocket = new ServerSocket(7758);
            init();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init() throws IOException {
        while (true){
            Socket s = serverSocket.accept();
            System.out.println( "《JOI》\tIP:"+s.getInetAddress()+"已连接,对方端口为:"+s.getPort()+ServerUtil.getDateInNow());
            new Thread(new ServerThread(s)).start();
        }
    }

    public void fdState(String uID) throws IOException {
        User user = dao.findUser(uID);
        if (user.isOnline()){
            dao.getUserOutMap().remove(uID);
        }
        user.setOnline(!user.isOnline());
        for (User u:user.getFriends()){
            if (u.isOnline()){
                ObjectOutputStream oos = dao.getUserOutMap().get(u.getID());
                oos.writeObject(new Message(MessageType.NOTICE,"fdState",uID,null));
            }
        }
    }


    public void join(String mFromID, String password, ObjectOutputStream oos, InetAddress inetAddress) throws IOException {
        User sUser = dao.findUser(mFromID);
        if (sUser == null||!password.equals(sUser.getPassword())) {
            oos.writeObject(new Message(MessageType.JOIN, "NO", null, null));
        } else   if (sUser.isOnline()){
            oos.writeObject(new Message(MessageType.JOIN,"isOnline",null,null));
        } else {
            dao.addUserOut(mFromID,oos);
            fdState(mFromID);
            oos.writeObject(new Message(MessageType.JOIN,"OK",null,copyUser(sUser,true)));
            ArrayList<Message>offlineMsg = dao.getOfflineMsg(mFromID);
            if (!offlineMsg.isEmpty()){
                for (Message message:offlineMsg) {
                    sendMsg(mFromID,message);
                }
                offlineMsg.removeAll(offlineMsg);
            }
            System.out.println("《JOI》\tUID: "+mFromID+" ,登录成功,IP:"+inetAddress+ ServerUtil.getDateInNow());
        }
    }

    public User copyUser(User sUser,boolean hasFriend){
        User u = new User();
        u.setOnline(sUser.isOnline());
        u.setID(sUser.getID());
        u.setPortrait(sUser.getPortrait());
        u.setName(sUser.getName());
        u.setMotto(sUser.getMotto());
        u.setFriends(sUser.getFriends());
        if (hasFriend){
            ArrayList<User> frListCopy = new ArrayList<User>();
            for (User uu:u.getFriends()){
                User uuu = new User();
                uuu.setOnline(uu.isOnline());
                uuu.setID(uu.getID());
                uuu.setPortrait(uu.getPortrait());
                uuu.setName(uu.getName());
                uuu.setMotto(uu.getMotto());
                uuu.setFriends(null);
                frListCopy.add(uuu);
            }
            u.setFriends(frListCopy);
        }
        return u;
    }

    public void message(Message message) throws IOException{
        //通过消息中所含的ID在数据库中找到用户并获取相应的对象输出流
        sendMsg(message.getToID(),message);
    }

    public void registerNotice( Object data, Socket socket,ObjectOutputStream oos) throws IOException {
       User user = (User) data;
       if (dao.containsUID(user.getID())){
           return;
       }
       String uID = String.valueOf(100001+dao.userCount());
       user.setID(uID);
        dao.addUser(user);
        System.out.println("《REG》\tIP:"+socket.getInetAddress()+",端口:"+socket.getPort()+",成功注册UID: "+uID+ ServerUtil.getDateInNow());
        oos.writeObject(new Message(MessageType.NOTICE,"register",uID,null));
    }

    public void addUserOK(String addID, String fromID) throws IOException {
        if (!dao.containsUID(addID)){
            return;
        }
       User toUser =  dao.findUser(addID);
       User fromUser = dao.findUser(fromID);
        if (fromUser.getFriends().contains(toUser)){
            return;
        }
       fromUser.getFriends().add(toUser);
       toUser.getFriends().add(fromUser);
        System.out.println("《ADD》\tUID:"+addID+",成功添加UID："+fromID+"为好友"+ServerUtil.getDateInNow());
       sendMsg(fromID ,new Message(MessageType.NOTICE,"addUserOK",null,copyUser(toUser,false)));
       sendMsg(addID,new Message(MessageType.NOTICE,"addUserOK",null,copyUser(fromUser,false)));
       sendMsg(addID,new Message(MessageType.MESSAGE,fromID,addID,"我们已经是好友啦,快来聊天吧~"));
       sendMsg(fromID,new Message(MessageType.MESSAGE,addID,fromID,"我们已经是好友啦,快来聊天吧~"));
    }

    private void sendMsg(String toID ,Message message) throws IOException {
        if (dao.findUser(toID).isOnline())
            dao.getUserOut(toID).writeObject(message);
        else {
            dao.getOfflineMsg(toID).add(message);
        }
    }

    public void addUserNotice(String toID, String fromID) throws IOException {
        User to = dao.findUser(toID);
        User from = dao.findUser(fromID);
        if (from.getFriends().contains(to)||toID.equals(fromID)||!dao.containsUID(toID)){
            return;
        }
        sendMsg(toID,new Message(MessageType.NOTICE,"addUserNotice",null,from));


    }

    public void upDataByUser(String mFromID,String pw, User user) throws IOException {
        User sUser = dao.findUser(mFromID);
        sUser.setName(user.getName());
        sUser.setPortrait(user.getPortrait());
        sUser.setPassword(pw);
        for (User u : sUser.getFriends()){
            u.getFriends().remove(sUser);
            u.getFriends().add(sUser);
            if (u.isOnline()){
                dao.getUserOut(u.getID()).writeObject(new Message(MessageType.NOTICE,"upDataByUser",null,copyUser(sUser,true)));
            }
        }


    }

    public void useQuit(String uID) throws IOException {
        if (uID!=null&& dao.containsUID(uID)){

            System.out.println("《QUI》\tUID: "+uID+" ,退出登录"+ ServerUtil.getDateInNow());
            Action.action.fdState(uID);
        }
    }

}
