package cn.com.server.action;


import cn.com.pojo.Message;
import cn.com.pojo.User;
import cn.com.server.util.ServerUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread  implements Runnable  {

    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
     private Message m;
    private String mFromID ;
    private boolean isConnected;

    public ServerThread(Socket socket)  {
        this.socket = socket;
        isConnected = true;
    }

    @Override
    public void run() {
        try {
            socket.setSoTimeout(0);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        while (isConnected){
            m = (Message) objectInputStream.readObject();
            switch (m.getMessageType()){
                //用户加入
                case JOIN:
                    mFromID = m.getFromID();
                    Action.action.join(mFromID,(String) m.getData(),objectOutputStream,socket.getInetAddress());
                    break;
                //通知
                case NOTICE:
                    if (m.getFromID().equals("register")){
                        Action.action.registerNotice(m.getData(),socket,objectOutputStream);
                    }else if (m.getFromID().equals("addUser")){
                        Action.action.addUserNotice(m.getToID(),(String)m.getData());
                }else if (m.getFromID().equals("addAccept")){
                        Action.action.addUserOK(m.getToID(),(String)m.getData());
                    }else if (m.getFromID().equals("upDataByUser")){
                        Action.action.upDataByUser(mFromID,m.getToID(),(User)m.getData());
                    }else if(m.getFromID().equals("heartBeat")){
                        break;
                    }

                    break;
                //通知
                case MESSAGE:
                    Action.action.message(m);
                    break;
                //用户退出
                case QUIT:
                    isConnected = false;
                    Action.action.useQuit(mFromID);
                    break;
                //错误
                case ERROR:
                    break;
            }
        }
        } catch (SocketException e){
//            e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isConnected){
                    Action.action.useQuit(mFromID);
                }
                System.out.println( "《QUI》\tIP:"+socket.getInetAddress()+"已断开连接,对方端口为:"+socket.getPort()+ ServerUtil.getDateInNow());
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
