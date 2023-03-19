package cn.com.view.viewutil;

import cn.com.pojo.User;

import javax.swing.*;
import java.awt.*;

/**
 * DefaultListCellRenderer是Swing JList的渲染器
 * ImageCellRender继承DefaultListCellRender,DefaultListCellRender继承于JLabel，所以只要将图片赋给JLabel就可以更改显示样式
 * @author Silly
 *
 */
public class FriendsCellRender extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;
    User user;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (user==null){
            return;
        }
        g.setColor(new Color(0xbfbfbf));
        g.setColor(Color.lightGray);
        g.drawLine(10, getHeight() -1, getWidth()-10, getHeight()-1 );
        if (user.isOnline()){
            g.setColor(Style.greenColor);
        }else {
            g.setXORMode(Color.gray);
        }
        g.fillOval(240,25,10,10);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus){
        //instanceof 判断其左边对象是否为其右边类的实例
        if(value instanceof User){
            user = (User) value;
            String msg = user.getMotto();
            if (msg==null){
               msg = "";
            }else if (msg.length()>=12) {
               msg = msg.substring(0, 12);
               msg += "...";
            }
            setText("<html>"+user.getName()+"<div style=\"color: gray;font-size:10px\" >"+msg+"</div></html>");
            ImageIcon icon = new ImageIcon(user.getPortrait().getImage());
            icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
            setIcon(icon);
            setFont(new Font("黑体",0,18));
            //设置文本的水平和垂直位置:比如（右上）
            setVerticalTextPosition(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.RIGHT);
            setBackground(Style.nullColor);
        }

            if   (isSelected) {
                setBackground(Color.LIGHT_GRAY);
                setForeground(Style.bluColor);
            }
            else {
                //设置选取与取消选取的前景与背景颜色.
                setForeground(Style.pinColor);
            }
        return this;
    }
}
