package cn.com.thread;


import cn.com.action.ClientAction;
import cn.com.pojo.Message;
import cn.com.pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientListener implements Runnable{

    private Socket socket;
    private HeartBeat heartBeat;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String hostname;
    private int port;
    private boolean isConnected;

    public ClientListener(String hostname, int port)  {
        isConnected = false;
        heartBeat = new HeartBeat(50000,this);
        this.hostname = hostname;
        this.port = port;
    }

    private void connect() throws IOException {
        while (!isConnected){
            try {
                socket = new Socket(hostname,port);
                objectInputStream = new ObjectInputStream( socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                socket.setSoTimeout(0);
                isConnected = true;
                heartBeat.start();
                if (!ClientAction.action.isNullLV()){
                    ClientAction.action.connectMSG(isConnected);
                }
            } catch (IOException e) {
                if (socket!=null){
                    socket.close();
                }
                System.out.println("与服务器连接失败,错误代码:0");
                System.out.println("正在尝试连接...");
                isConnected = false;
                if (!ClientAction.action.isNullLV()){
                    ClientAction.action.connectMSG(isConnected);
                }
                heartBeat.stop();
                connect();
            }
        }

    }

    public void stopHeartBeat(){
        heartBeat.stop();
    }
    @Override
    public void run() {
        while (true){
            try {
                connect();
                while (isConnected){
                    Message message = (Message)objectInputStream.readObject();
                    switch (message.getMessageType()){
                        case JOIN:
                            ClientAction.action.loginOK(message.getFromID(),(User)message.getData());
                            break;
                        case MESSAGE:
                            String msg = (String)message.getData();
                            String fromID = message.getFromID();
                            ClientAction.action.receiveMsg(fromID,msg);
                            break;
                        case NOTICE:
                            if (message.getFromID().equals("fdState")){
                                ClientAction.action.fdState(message.getToID());
                            }else if (message.getFromID().equals("register")){
                                ClientAction.action.registerOK(message.getToID());
                            }else if (message.getFromID().equals("addUserOK")){
                                ClientAction.action.addUserOK((User) message.getData());
                            }else if (message.getFromID().equals("addUserNotice")){
                                ClientAction.action.addUserNotice((User) message.getData());
                            }else if (message.getFromID().equals("upDataByUser")){
                                ClientAction.action.upDataByUserOK((User) message.getData());
                            }
                    }

                }
            }catch (SocketException e){
                System.out.println("与服务器断开连接,错误代码:1");
//                e.printStackTrace();
            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
            }finally {
                isConnected = false;
                if (socket!=null){
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }

    public boolean isConnect() {
        return isConnected;
    }
    public void closeSocket() {
        if(socket==null){
            return;
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(Message message)  {
        if (isConnected){
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.reset();
            } catch (IOException e) {
                System.out.println("向服务器发送数据失败,错误代码:2");
                e.printStackTrace();
                isConnected = false;
            }
        }
    }
}
