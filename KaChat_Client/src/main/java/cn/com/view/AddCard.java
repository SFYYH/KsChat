package cn.com.view;

import cn.com.action.ClientAction;
import cn.com.pojo.User;
import cn.com.util.ResourcesUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddCard extends JLabel {
    private AddCard self;
    private Object value;
    private User user;
    private ImageIcon imageIcon;
    private JLabel labelIcon;
    private JLabel acceptButton;
    private JLabel rejectedButton;
    private JLabel text;
    public AddCard(Object value){
        self = this;
        this.value = value;
        labelIcon = new JLabel();
        acceptButton = new JLabel();
        rejectedButton = new JLabel();
        text = new JLabel();
        if (value instanceof User){
            user = (User) value;
            isUser();
        }

        init();
        assemble();
        setAction();
    }

    private void isUser() {
        imageIcon = user.getPortrait();
        text.setText("<html>" +
                "<div style=\"font-family:Sofia;font-size:13px;color:#f29a76;margin:2px;font-weight: bold\" >"+user.getName()+" (UID:"+user.getID()+")</div>" +
                "<div style=\"font-family:Sofia;font-size:10px;color:gray;margin:3px;font-weight: bold\" ></div>" +
                "<div style=\"font-family:Sofia;font-size:12px;margin:3px;font-weight: bold\" >想添加您为好友</div></html>");
    }

    private void init() {
        setLayout(null);
        setSize(365,220);
        setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/addcardbg.png", "addcardbg", ".png").getAbsolutePath()));

        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH));
        labelIcon.setIcon(imageIcon);
        labelIcon.setBounds(25,25,100,100);

        text.setBounds(140,25,300,100);


        acceptButton.setBounds(560,25,100,30);
        acceptButton.setHorizontalTextPosition(SwingConstants.CENTER);
        acceptButton.setText("同意");
        acceptButton.setForeground(Color.white);
        acceptButton.setFont(new Font("黑体",0,20));
        acceptButton.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/accept.png", "accept", ".png").getAbsolutePath()));

        rejectedButton.setBounds(560,95,100,30);
        rejectedButton.setHorizontalTextPosition(SwingConstants.CENTER);
        rejectedButton.setText("忽略");
        rejectedButton.setForeground(Color.white);
        rejectedButton.setFont(new Font("黑体",0,20));
        rejectedButton.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/rejected.png", "rejected", ".png").getAbsolutePath()));
    }

    private void assemble() {
        add(labelIcon);
        add(text);
        add(acceptButton);
        add(rejectedButton);
    }

    private void setAction() {
        acceptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String uID = ((User)self.value).getID();
                ClientAction.action.addAccept(self,uID);
            }
        });

        rejectedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ClientAction.action.addRejected(self);
            }
        });
    }
}
