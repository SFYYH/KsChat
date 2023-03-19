package cn.com.view.viewutil;

import javax.swing.*;
import java.awt.*;

public class MiniSizeLabel extends JLabel {
    public MiniSizeLabel(){
        setText("●");
        setBounds(645,5,20,20);
        setFont(new Font("黑体", Font.PLAIN,20));
        setForeground(Style.bluColor);
    }
}
