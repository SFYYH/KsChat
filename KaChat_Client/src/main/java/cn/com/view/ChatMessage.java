package cn.com.view;

import javax.swing.*;
import java.awt.*;

public class ChatMessage extends JPanel {
    boolean isSelf;
    ImageIcon icon;
    String msg;
    String name;
    JLabel userIM;

     public ChatMessage( ImageIcon icon,String msg,boolean isSelf){
         this.icon = icon;
         this.msg = msg;
         this.isSelf = isSelf;
         init();
     }



    public ChatMessage( ImageIcon icon,String msg,boolean isSelf,String name){
        this.icon =  new ImageIcon(icon.getImage());
        this.msg = msg;
        this.isSelf = isSelf;
        this.name = name;
        init();
    }

    private void init() {
        userIM = new JLabel();
        icon.setImage(icon.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
        userIM.setIcon(icon);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(5);
        borderLayout.setHgap(5);
        setLayout(borderLayout);
        StringBuffer br1 = new StringBuffer(msg);
        for (int i = 15 ;i<= br1.length();i+=19) {
            br1.insert(i,"<br>");
        }
        if (isSelf){
            userIM.setText("<html>"+""+"<div style=\"font-family:Microsoft YaHei;margin:5px;font-size:10px;background:#0181cc;color:#ffffff;padding:5px;\" >"+br1+"</html>");
            add(userIM,BorderLayout.EAST);
            userIM.setHorizontalTextPosition(SwingConstants.LEFT);
        } else {
            if (name==null){
                name = "";
            }
            userIM.setText("<html>"+name+"<div style=\"font-family:Microsoft YaHei;margin:5px;font-size:10px;background:#eb6877;color:#ffffff;padding:5px;\" >"+br1+"</html>");
            add(userIM,BorderLayout.WEST);
            userIM.setHorizontalTextPosition(SwingConstants.RIGHT);
        }

    }

}
