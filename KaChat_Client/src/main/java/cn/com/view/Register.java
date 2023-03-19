package cn.com.view;

import cn.com.action.ClientAction;
import cn.com.util.ResourcesUtils;
import cn.com.view.animate.Animate;
import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register extends JLabel {
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

    public JLabel uIDLabel;
    public JLabel uNameLabel;
    public JLabel pwLabel;
    public JLabel pwrLabel;
    public JLabel buttonL;
    public JLabel userIcon;
    public ImageIcon defaultIcon;
    Register(){


        uIDLabel = new JLabel();
        uNameLabel = new JLabel();
        pwLabel = new JLabel();
        pwrLabel = new JLabel();
        uIDText = new JTextField("用户注册");
        uNameText = new JTextField("请输入用户名");
        pwText = new JPasswordField("请输入密码");
        pwrText = new JPasswordField("请再次输入密码");
        buttonL = new JLabel();
        userIcon = new JLabel();
        textIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/text.png", "text", ".png").getAbsolutePath());
        rIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/rtext.png", "rtext", ".png").getAbsolutePath());
        yIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/ytext.png", "ytext", ".png").getAbsolutePath());
        gIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/gtext.png", "gtext", ".png").getAbsolutePath());
        bIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/btext.png", "btext", ".png").getAbsolutePath());
        defaultIcon = Style.addIcon;
        init();
        assemble();
        setAction();
    }

    private void init() {
        userIcon.setBounds(80,200,140,140);
        userIcon.setIcon(defaultIcon);
        userIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        userIcon.setVerticalTextPosition(SwingConstants.EAST);
        userIcon.setForeground(Color.gray);
        userIcon.setFont(new Font("黑体", Font.BOLD,12));
        userIcon.setText("选择头像");

        uIDLabel.setBounds(250,50,440,70);
        uIDLabel.setIcon(bIcon);
        uIDLabel.setLayout(null);

        uIDText.setBounds(20,0,400,70);
        uIDText.setOpaque(false);
        uIDText.setEditable(false);
        uIDText.setFont(new Font("黑体",Font.CENTER_BASELINE,30));
        uIDText.setBorder(Style.nullBorder);
        uIDText.setHorizontalAlignment(JTextField.CENTER);
        uIDText.setForeground(Color.white);

        uNameLabel.setBounds(250,145,440,70);
        uNameLabel.setIcon(textIcon);
        uNameLabel.setLayout(null);


        uNameText.setBounds(20,0,400,70);
        uNameText.setOpaque(false);
        uNameText.setDragEnabled(true);
        uNameText.setBorder(Style.nullBorder);
        uNameText.setHorizontalAlignment(JTextField.CENTER);
        uNameText.setFont(Style.textFont);

        pwLabel.setBounds(250,240,440,70);
        pwLabel.setIcon(textIcon);
        pwLabel.setLayout(null);

        pwText.setBounds(20,0,400,70);
        pwText.setOpaque(false);
        pwText.setBorder(Style.nullBorder);
        pwText.setHorizontalAlignment(JTextField.CENTER);
        pwText.setFont(Style.textFont);
        pwText.setEchoChar((char)0);

        pwrLabel.setBounds(250,335,440,70);
        pwrLabel.setIcon(textIcon);
        pwrLabel.setLayout(null);

        pwrText.setBounds(20,0,400,70);
        pwrText.setOpaque(false);
        pwrText.setBorder(Style.nullBorder);
        pwrText.setFont(Style.textFont);
        pwrText.setHorizontalAlignment(JTextField.CENTER);
        pwrText.setEchoChar((char)0);

        buttonL.setBounds(250,430,440,70);
        buttonL.setIcon(rIcon);
        buttonL.setText("注册");
        buttonL.setFont(new Font("黑体",Font.CENTER_BASELINE,30));
        buttonL.setForeground(Color.white);
        buttonL.setHorizontalTextPosition(SwingConstants.CENTER);


    }


    private void assemble() {
        uIDLabel.add(uIDText);
        uNameLabel.add(uNameText);
        pwLabel.add(pwText);
        pwrLabel.add(pwrText);

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
                if (ClientAction.action.textFocusGained(uNameText,"1~10位的中文,英文,数字或空格")){
                    ClientAction.action.textFocusGained(uNameText,"请输入用户名");
                }
                uNameLabel.setIcon(gIcon);
                uNameText.setForeground(Color.white);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!ClientAction.action.textFocusLost(uNameText,"请输入用户名")){
                    if(!ClientAction.action.validate(uNameText.getText(),"^[\u4E00-\u9FA5A-Za-z0-9 ]{1,10}$")){
                        uNameLabel.setIcon(yIcon);
                        Animate.dither(uNameLabel);
                        uNameText.setText("1~10位的中文,英文,数字或空格");
                    }
                } else {
                    uNameLabel.setIcon(textIcon);
                    uNameText.setForeground(Color.black);
                }
            }
        });
        pwText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(ClientAction.action.pwFocusGained(pwText,"只能为6~8位的数字或字母")){
                    ClientAction.action.pwFocusGained(pwText,"请输入密码");
                }
                pwLabel.setIcon(gIcon);
                pwText.setForeground(Color.white);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!ClientAction.action.pwFocusLost(pwText,"请输入密码")){
                    if(!ClientAction.action.validate(pwText.getText(),"^\\w{6,8}$")&&!pwText.getText().equals("")){
                        pwLabel.setIcon(yIcon);
                        Animate.dither(pwLabel);
                        pwText.setEchoChar((char) 0);
                        pwText.setText("只能为6~8位的数字或字母");
                    }
                } else {
                    pwLabel.setIcon(textIcon);
                    pwText.setForeground(Color.black);
                }

            }
        });

        pwrText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ClientAction.action.pwFocusGained(pwrText,"输入的密码不一致");
                ClientAction.action.pwFocusGained(pwrText,"请再次输入密码");
                pwrLabel.setIcon(gIcon);
                pwrText.setForeground(Color.white);
            }

            @Override
            public void focusLost(FocusEvent e) {

                if(!(pwrText.getText().equals("")||pwText.getText().equals("请输入密码"))){
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
                    ClientAction.action.pwFocusLost(pwrText,"请再次输入密码");
            }
        });

        userIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                userImageIcon = ClientAction.action.loadUserIcon(userIcon);
            }
        });

        buttonL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (uNameLabel.getIcon()!=gIcon){
                    Animate.dither(buttonL);
                    Animate.dither(uNameLabel);
                }else if (pwLabel.getIcon()!=gIcon){
                    Animate.dither(buttonL);
                    Animate.dither(pwLabel);
                }else if (pwrLabel.getIcon()!=gIcon){
                    Animate.dither(buttonL);
                    Animate.dither(pwrLabel);
                }else if (userIcon.getIcon()==defaultIcon){
                    Animate.dither(buttonL);
                    Animate.dither(userIcon);
                }else if (buttonL.getText().equals("注册成功√")){
                    Animate.dither(buttonL);
                    Animate.dither(uIDLabel);
                }else if (buttonL.getText().equals("注册")){
                    buttonL.setText("正在注册请稍后...");
                    ClientAction.action.sendRegisterNotice(uNameText.getText(),pwText.getPassword(),userImageIcon);
                }
            }
        });
    }
}
