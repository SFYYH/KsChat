package cn.com.view.viewutil;

import javax.swing.*;
import java.awt.*;

public class QuitLabel extends JLabel {

    public QuitLabel(){
        setText("●");
        setBounds(670,5,20,20);
        setFont(new Font("黑体", Font.PLAIN,20));
        setForeground(Style.pinColor);
    }
}
