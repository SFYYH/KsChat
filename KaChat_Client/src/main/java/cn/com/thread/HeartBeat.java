package cn.com.thread;

import cn.com.pojo.Message;
import cn.com.pojo.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeartBeat {
    private ClientListener clientListener;
    private Timer timer;
    private int delay;
    HeartBeat(int delay, ClientListener clientListener){
        this.delay = delay;
        this.clientListener = clientListener;
        init();
    }

    private void init() {
        this.timer = new Timer(delay, new sendHeartBeat());;
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }

    private class sendHeartBeat implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientListener.send(new Message(MessageType.NOTICE,"heartBeat",null,null));
        }
    }

}
