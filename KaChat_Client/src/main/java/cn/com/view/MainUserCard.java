package cn.com.view;

import cn.com.action.ClientAction;
import cn.com.dao.ClientDAO;
import cn.com.pojo.User;
import cn.com.util.ResourcesUtils;
import cn.com.view.animate.Animate;
import cn.com.view.viewutil.MiniSizeLabel;
import cn.com.view.viewutil.QuitLabel;
import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainUserCard extends JLabel{
    public ImageIcon userImageIcon;
    public ImageIcon bIcon;
    public ImageIcon textIcon;
    public ImageIcon rIcon;
    public ImageIcon yIcon;
    public ImageIcon gIcon;
    public JTextField uIDText;
    public JTextField uNameText;
    public JPasswordField pwText;
    public JPasswordField pwrText;

    JLabel quit;
    JLabel minimSize;
    public JLabel uIDLabel;
    public JLabel uNameLabel;
    public JLabel pwLabel;
    public JLabel pwrLabel;
    public JLabel buttonL;
    public JLabel userIcon;
    Font textFont ;
    User user = ClientDAO.getUser();
    ImageIcon imageIcon;
    MainUserCard(){

        quit = new QuitLabel();
        minimSize = new MiniSizeLabel();
        uIDLabel = new JLabel();
        uNameLabel = new JLabel();
        pwLabel = new JLabel();
        pwrLabel = new JLabel();
        uIDText = new JTextField("UID:"+user.getID());
        uNameText = new JTextField("用户名:"+user.getName());
        pwText = new JPasswordField("请输入需要修改的密码");
        pwrText = new JPasswordField("请再次输入密码");
        buttonL = new JLabel();
        userIcon = new JLabel();
        textIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user/text.png", "text", ".png").getAbsolutePath());
        rIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user/rtext.png", "rtext", ".png").getAbsolutePath());
        yIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user/ytext.png", "ytext", ".png").getAbsolutePath());
        gIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user/gtext.png", "gtext", ".png").getAbsolutePath());
        bIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user/btext.png", "btext", ".png").getAbsolutePath());
        textFont =  new Font("黑体",0,20);
        imageIcon = new ImageIcon(user.getPortrait().getImage());
        init();
        assemble();
        setAction();
    }

    private void init() {
        setFocusable(true);
        setBounds(0,0,700,620);
        setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user/usercardbg.png", "usercardbg", ".png").getAbsolutePath()));
        setLayout(null);

        userIcon.setBounds(290,10,130,130);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(120,120,Image.SCALE_AREA_AVERAGING));
        userIcon.setIcon(imageIcon);
        userIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        userIcon.setVerticalTextPosition(SwingConstants.EAST);
        userIcon.setForeground(Style.pinColor);

        uIDLabel.setBounds(200,190,300,35);
        uIDLabel.setIcon(bIcon);
        uIDLabel.setLayout(null);

        uIDText.setBounds(10,0,280,35);
        uIDText.setOpaque(false);
        uIDText.setEditable(false);
        uIDText.setForeground(Color.white);
        uIDText.setFont(new Font("黑体",Font.CENTER_BASELINE,25));
        uIDText.setBorder(Style.nullBorder);
        uIDText.setHorizontalAlignment(JTextField.CENTER);


        uNameLabel.setBounds(uIDLabel.getX(),uIDLabel.getY()+80,300,35);
        uNameLabel.setIcon(textIcon);
        uNameLabel.setLayout(null);


        uNameText.setBounds(uIDText.getBounds());
        uNameText.setOpaque(false);
        uNameText.setDragEnabled(true);
        uNameText.setEditable(false);
        uNameText.setFont(textFont);
        uNameText.setBorder(Style.nullBorder);
        uNameText.setHorizontalAlignment(JTextField.CENTER);


        pwLabel.setSize(uIDLabel.getSize());
        pwLabel.setLocation(999,999);
        pwLabel.setIcon(textIcon);
        pwLabel.setLayout(null);

        pwText.setBounds(uIDText.getBounds());
        pwText.setOpaque(false);
        pwText.setEditable(false);
        pwText.setBorder(Style.nullBorder);
        pwText.setFont(textFont);
        pwText.setHorizontalAlignment(JTextField.CENTER);
        pwText.setEchoChar((char)0);

        pwrLabel.setSize(uIDLabel.getSize());
        pwrLabel.setLocation(999,999);
        pwrLabel.setIcon(textIcon);
        pwrLabel.setLayout(null);

        pwrText.setBounds(uIDText.getBounds());
        pwrText.setOpaque(false);
        pwrText.setEditable(false);
        pwrText.setFont(textFont);
        pwrText.setBorder(Style.nullBorder);
        pwrText.setHorizontalAlignment(JTextField.CENTER);
        pwrText.setEchoChar((char)0);

        buttonL.setBounds(uNameLabel.getX(),uNameLabel.getY()+35+80+35+100,300,35);
        buttonL.setIcon(bIcon);
        buttonL.setText("修改个人资料");
        buttonL.setFont(new Font("黑体",Font.CENTER_BASELINE,22));
        buttonL.setForeground(Color.white);
        buttonL.setHorizontalTextPosition(SwingConstants.CENTER);


    }


    private void assemble() {
        uIDLabel.add(uIDText);
        uNameLabel.add(uNameText);
        pwLabel.add(pwText);
        pwrLabel.add(pwrText);

        add(quit);
        add(minimSize);
        add(uIDLabel);
        add(uNameLabel);
        add(pwLabel);
        add(pwrLabel);
        add(buttonL);
        add(userIcon);
    }



    private void setAction() {
        uNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (uNameText.isEditable()) {
                    if (ClientAction.action.textFocusGained(uNameText,"用户名:"+user.getName())){
                        ClientAction.action.textFocusGained(uNameText,"1~10位中文英文数字或空格");
                    }
                    uNameLabel.setIcon(gIcon);
                    uNameText.setForeground(Color.white);
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (uNameText.isEditable()) {
                    if(!ClientAction.action.textFocusLost(uNameText,"用户名:"+user.getName())){
                        if(!ClientAction.action.validate(uNameText.getText(),"^[\u4E00-\u9FA5A-Za-z0-9 :]{1,10}$")){
                            uNameLabel.setIcon(yIcon);
                            Animate.dither(uNameLabel);
                            uNameText.setText("1~10位中文英文数字或空格");
                        }
                    } else {
                        uNameLabel.setIcon(textIcon);
                        uNameText.setForeground(Color.black);
                    }
                }
            }
        });
        pwText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (pwText.isEditable()) {
                    if(ClientAction.action.pwFocusGained(pwText,"6~8位的数字或字母")){
                        ClientAction.action.pwFocusGained(pwText,"请输入需要修改的密码");
                    }
                    pwLabel.setIcon(gIcon);
                    pwText.setForeground(Color.white);

                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pwText.isEditable()) {
                    if(!ClientAction.action.pwFocusLost(pwText,"请输入需要修改的密码")){
                        if(!ClientAction.action.validate(pwText.getText(),"^\\w{6,8}$")){
                            pwLabel.setIcon(yIcon);
                            Animate.dither(pwLabel);
                            pwText.setEchoChar((char) 0);
                            pwText.setText("6~8位的数字或字母");
                        }
                    } else {
                        pwLabel.setIcon(textIcon);
                        pwText.setForeground(Color.black);
                    }
                }

            }
        });

        pwrText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (pwrText.isEditable()) {
                    ClientAction.action.pwFocusGained(pwrText,"输入的密码不一致");
                    ClientAction.action.pwFocusGained(pwrText,"请再次输入密码");
                    pwrLabel.setIcon(gIcon);
                    pwrText.setForeground(Color.white);
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pwrText.isEditable()) {
                    ClientAction.action.pwFocusLost(pwrText,"请再次输入密码");
                    if(!(pwText.getText().equals("")||pwText.getText().equals("请输入需要修改的密码"))){
                        if (!pwrText.getText().equals(pwText.getText())){
                            Animate.dither(pwrLabel);
                            pwrLabel.setIcon(yIcon);
                            pwrText.setEchoChar((char) 0);
                            pwrText.setText("输入的密码不一致");
                        }
                    }else {
                        pwrLabel.setIcon(textIcon);
                        pwrText.setForeground(Color.black);
                    }
                }


            }
        });

        userIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (userIcon.getIcon()!=imageIcon) {
                    userImageIcon =  ClientAction.action.loadUserIcon(userIcon);
                }

            }
        });

        buttonL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (buttonL.getIcon()==rIcon) {
                    if (uNameLabel.getIcon()!=gIcon){
                        Animate.dither(buttonL);
                        Animate.dither(uNameLabel);
                    }else if (pwLabel.getIcon()!=gIcon){
                        Animate.dither(buttonL);
                        Animate.dither(pwLabel);
                    }else if (pwrLabel.getIcon()!=gIcon){
                        Animate.dither(buttonL);
                        Animate.dither(pwrLabel);
                    }else if (userIcon.getIcon()==Style.addIcon){
                        Animate.dither(buttonL);
                        Animate.dither(userIcon);
                    }else if (buttonL.getText().equals("注册成功")){
                        Animate.dither(buttonL);
                    }else {
                        ClientAction.action.upDataByUser(uNameText.getText(),pwText.getText(),userImageIcon);
                        uNameText.setEditable(false);
                        uNameText.setForeground(Color.black);
                        uNameText.setText("用户名:"+uNameText.getText());
                        pwText.setEditable(false);
                        pwText.setForeground(Color.black);
                        pwrText.setEditable(false);
                        pwrText.setForeground(Color.black);
                        pwLabel.setLocation(999,999);
                        pwrLabel.setLocation(999,999);
                        ImageIcon imageIcon = new ImageIcon(user.getPortrait().getImage());
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(120,120,Image.SCALE_AREA_AVERAGING));
                        userIcon.setIcon(imageIcon);
                        uNameLabel.setIcon(textIcon);
                        pwLabel.setIcon(textIcon);
                        pwrLabel.setIcon(textIcon);
                        buttonL.setText("修改成功√");
                        buttonL.setIcon(gIcon);
                    }
                }else {

                    uNameText.setEditable(true);
                    pwText.setEditable(true);
                    pwrText.setEditable(true);
                    userIcon.setIcon(Style.addIcon);
                    buttonL.setText("提交修改");
                    buttonL.setIcon(rIcon);
                    pwLabel.setLocation(uNameLabel.getX(),uNameLabel.getY()+80);
                    pwText.setEchoChar((char) 0);
                    pwText.setText("请输入需要修改的密码");
                    pwrLabel.setLocation(pwLabel.getX(),pwLabel.getY()+80);
                    pwrText.setEchoChar((char) 0);
                    pwrText.setText("请再次输入密码");
                }

            }
        });
    }

}
