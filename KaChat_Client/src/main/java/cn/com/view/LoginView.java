package cn.com.view;


import cn.com.action.ClientAction;
import cn.com.util.ResourcesUtils;
import cn.com.view.animate.LodingJLabel;
import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LoginView extends JFrame {
    private LoginView self;

    private int fWidth;
    private int fHeight;

    ImageIcon textIcon;
    ImageIcon textClickIcon;
    ImageIcon buttonIcon;
    ImageIcon buttonClickIcon;

    JLabel login;
    JLabel left;
    JLabel quit;
    JLabel munuBar;
    JLabel minimize;

    public JLabel bg1;
    public JLabel bg2;
    public CardLayout bgCardLayout;
    public JLabel uidL;
    public JLabel pwdL;
    public JLabel msg;
    public JLabel buttonL;
    public JLabel buttonClickL;
    public JTextField uIDText;
    public JPasswordField pwgText;
    public JLabel userIconL;
    public LodingJLabel lodingJLabel;
    private Point windowPoint;

    Font buttonFont;
    Font msgFont;
    public JLabel registerIcon;
    public Register register;

    JLabel fPicture;
    public List<Component> pictures ;

    public LoginView(){
        self = this;
        fWidth=1400;
        fHeight=750;


        bg1 = new JLabel();
        bg2 = new JLabel();
        bgCardLayout = new CardLayout();
        windowPoint = new Point();

        lodingJLabel = new LodingJLabel(5,0,5);
        left = new JLabel();
        register = new Register();
        pictures = new ArrayList<Component>();
        fPicture = new JLabel();
        login = new JLabel();
        buttonClickL = new JLabel();
        quit = new JLabel("●");
        minimize = new JLabel("●");
        munuBar = new JLabel();
        userIconL = new JLabel();
        msg = new JLabel();
        uidL = new JLabel();
        pwdL = new JLabel();
        buttonL = new JLabel();
        textIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/text1.png", "text1", ".png").getAbsolutePath());
        textClickIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/text.png", "text", ".png").getAbsolutePath());
        buttonIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/button.png", "button", ".png").getAbsolutePath());
        buttonClickIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/button1.png", "button", ".png").getAbsolutePath());
        uIDText = new JTextField("请输入UID");
        pwgText = new JPasswordField("请输入密码");
        msgFont = new Font("黑体",Font.CENTER_BASELINE,20);
        buttonFont = new Font("黑体",Font.CENTER_BASELINE,30);
        registerIcon = new JLabel();
        init();
        assemble();
        setAction();
    }



    private void init() {

        setTitle("KaChat:登录界面");
        setLayout(null);
        setLocationRelativeTo(null);
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width-fWidth)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-fHeight)/2,fWidth,fHeight);
        setFocusable(true);
        setUndecorated(true);
        setBackground(Style.nullColor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bg1.setBounds(0,0,fWidth,fHeight);
        bg1.setLayout(bgCardLayout);

        bg2.setBounds(0,0,fWidth,fHeight);
        bg2.setLayout(null);



        login.setBounds(730,0,650,750);
        login.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/login.png", "login", ".png").getAbsolutePath()));

        left.setBounds(0,55,1400,570);
        left.setLayout(null);
        left.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/left.png", "left", ".png").getAbsolutePath()));

        register.setBounds(left.getX()+10,left.getY()+10,1380,550);
        register.setVisible(false);
        register.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/registerbg.png", "registerbg", ".png").getAbsolutePath()));
        register.setLayout(null);

        fPicture.setBounds(10,10,1380,550);
        fPicture.setLayout(null);
        fPicture.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/picture/picture4.png", "picture4", ".png").getAbsolutePath()));

        munuBar.setBounds(30,15,470,70);
        munuBar.setText("<html><div style=\"font-family:Microsoft YaHei;color: gray;font-size:12px\" >KaChat</div></html>");

        quit.setBounds(535,37,40,30);
        quit.setFont(buttonFont);
        quit.setForeground(Style.pinColor);

        minimize.setBounds(495,37,40,30);
        minimize.setFont(buttonFont);
        minimize.setForeground(Style.bluColor);

        userIconL.setBounds(240,95,120,120);
        ImageIcon icon = Style.Icon;
        icon.setImage(icon.getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH));
        userIconL.setIcon(icon);
        setIconImage(icon.getImage());

        lodingJLabel.setBounds(235,90,130,130);


        msg.setBounds(105,235,390,25);
        msg.setForeground(Style.pinColor);
        msg.setFont(msgFont);
        msg.setHorizontalAlignment(SwingConstants.CENTER);


        uidL.setOpaque(false);
        uidL.setBounds(80,235,500,150);
        uidL.setIcon(textIcon);


        pwdL.setOpaque(false);
        pwdL.setBounds(80,325,500,150);
        pwdL.setIcon(textIcon);

        uIDText.setOpaque(false);
        uIDText.setBounds(105,275,390,70);
        uIDText.setDragEnabled(true);
        uIDText.setFont(Style.textFont);
        uIDText.setBorder(Style.nullBorder);
        uIDText.setHorizontalAlignment(JTextField.CENTER);

        pwgText.setOpaque(false);
        pwgText.setBounds(105,365,390,70);
        pwgText.setFont(Style.textFont);
        pwgText.setBorder(Style.nullBorder);
        pwgText.setHorizontalAlignment(JTextField.CENTER);
        pwgText.setEchoChar((char)0);

        buttonL.setOpaque(false);
        buttonL.setBounds(65,470,500,150);
        buttonL.setIcon(buttonIcon);

        buttonClickL.setBounds(15,25,445,80);
        buttonClickL.setText("登录");
        buttonClickL.setForeground(Color.white);
        buttonClickL.setHorizontalAlignment(SwingConstants.CENTER);
        buttonClickL.setFont(buttonFont);


        registerIcon.setBounds(255,610,100,40);
        registerIcon.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/registericon.png", "registericon", ".png").getAbsolutePath()));
        registerIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        registerIcon.setText("<html><div style=\"font-family:Microsoft YaHei;color:white;font-size:12px\" >注册账号</div></html>");
    }
    private void assemble() {
        buttonL.add(buttonClickL);

        for (int i = 1; i <= 3; i++) {
            JLabel jLabel = new JLabel();
            jLabel.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/picture/picture" + i + ".png", "picture" + i + ".png", ".png").getAbsolutePath()));
            jLabel.setBounds(0,0,1380,550);
            pictures.add(jLabel);
            fPicture.add(jLabel);
        }
        left.add(fPicture);
        login.add(userIconL);
        login.add(msg);
        login.add(munuBar);
        login.add(minimize);
        login.add(quit);
        login.add(buttonL);
        login.add(pwgText);
        login.add(uIDText);
        login.add(pwdL);
        login.add(uidL);
        login.add(registerIcon);
        login.add(lodingJLabel);

        bg2.add(login);
        bg2.add(register);
        bg2.add(left);

        bg1.add(bg2);
        add(bg1);


    }

    public JLabel getMsg() {
        return msg;
    }






    private void setAction() {

        final Point origin = new Point();
        munuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();

            }
        });
        munuBar.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = getLocation();
                setLocation(point.x+e.getX()- origin.x,point.y+e.getY()- origin.y);
            }
        });

        minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientAction.action.miniSize(self,bg1,bgCardLayout,windowPoint,false);
            }
        });



        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientAction.action.useQuit(self,bg1,bgCardLayout,true);

            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e){
                ClientAction.action.miniSize(self,bg1,bgCardLayout,windowPoint,true);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ClientAction.action.useQuit(self,bg1,bgCardLayout,false);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                if (windowPoint.getX()== 0){
                    windowPoint.setLocation(self.getLocation());
                }
            }
        });


        uIDText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                ClientAction.action.textFocusLost(uIDText,"请输入UID");
                uIDText.setLocation(105,275);
                uidL.setLocation(80,235);
                uidL.setIcon(textIcon);
            }

            @Override
            public void focusGained(FocusEvent e) {

                ClientAction.action.textFocusGained(uIDText,"请输入UID");
                uIDText.setLocation(100,265);
                uidL.setLocation(65,235);
                uidL.setIcon(textClickIcon);
                msg.setText("");
            }
        });

        pwgText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
            ClientAction.action.pwFocusLost(pwgText,"请输入密码");
            pwgText.setLocation(105,365);
            pwdL.setLocation(80,325);
            pwdL.setIcon(textIcon);
            }
            @Override
            public void focusGained(FocusEvent e) {
                ClientAction.action.pwFocusGained(pwgText,"请输入密码");
                pwgText.setLocation(100,355);
                pwdL.setLocation(65,325);
                pwdL.setIcon(textClickIcon);
                msg.setText("");

            }

        });

        pwgText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    ClientAction.action.login();

                }
            }
        });

        buttonClickL.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonL.setLocation(buttonL.getX()-20,buttonL.getY()-20);
                buttonClickL.setLocation(buttonClickL.getX()+25,buttonClickL.getY()+25);
                buttonL.setIcon(buttonClickIcon);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                buttonL.setLocation(buttonL.getX()+20,buttonL.getY()+20);
                buttonClickL.setLocation(buttonClickL.getX()-25,buttonClickL.getY()-25);

                buttonL.setIcon(buttonIcon);

            }


            @Override
            public void mouseClicked(MouseEvent e) {
                lodingJLabel.show();
                buttonClickL.setText("正在登录请稍后...");
                ClientAction.action.login();
            }
        });

        registerIcon.addMouseListener(new MouseAdapter() {
            boolean switchOn = true;
            @Override
            public void mouseReleased(MouseEvent e) {
                if (switchOn){
                    register.setVisible(true);
                }else {
                    register.setVisible(false);
                }
                switchOn=!switchOn;
            }
        });
    }


}
