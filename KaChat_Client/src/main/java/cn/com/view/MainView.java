package cn.com.view;


import cn.com.action.ClientAction;
import cn.com.dao.ClientDAO;
import cn.com.util.ResourcesUtils;
import cn.com.view.animate.Animate;
import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainView extends JFrame {

    private JFrame self;
    private int fWidth;
    private int fHeight;

    JLabel mainView;
    JLabel menubarView;

    JLabel winMunuBar;

    public JLabel bg1 ;
    public JLabel bg2 ;
    public CardLayout bgCardLayout ;
    public JLabel mbUserIcon;
    public JLabel mbUser;
    public JLabel mbAdd;
    public JLabel mbGroup;
    public JLabel mbChat;
    public JLabel select;
    public JLabel dot;
    public JLabel mblogo;
    public Point windowPoint;

    private ImageIcon mbChatIcon;
    private ImageIcon mbAddIcon;
    private ImageIcon mbGroupIcon;
    private ImageIcon mbSetingIcon;

    JLabel quit;
    JLabel minimSize;
    private JLabel main;
    CardLayout cardLayout;
    private MainChatCard mainChatCard;
    private MainAddCard mainAddCard;
    private MainUserCard mainUserCard;


    public MainView(){
        fWidth=800;
        fHeight=700;


        bg1 = new JLabel();
        bg2 = new JLabel();
        bgCardLayout = new CardLayout();
        self = this;
        windowPoint = new Point( );

        mainView = new JLabel();
        menubarView = new JLabel();
        mbUserIcon = new JLabel();
        mbUser = new JLabel();
        mbChat = new JLabel();
        mbAdd = new JLabel();
        mbGroup = new JLabel();
        mblogo = new JLabel();
        select = new JLabel();
        dot = new JLabel();
        mbChatIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat.png", "chat", ".png").getAbsolutePath());
        mbGroupIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/group.png", "group", ".png").getAbsolutePath());
        mbAddIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add.png", "add", ".png").getAbsolutePath());
        mbSetingIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/seting.png", "seting", ".png").getAbsolutePath());

        cardLayout = new CardLayout(10,10);
        main = new JLabel();
        quit = new JLabel();
        minimSize = new JLabel();
        winMunuBar = new JLabel();
        mainChatCard = new MainChatCard(ClientDAO.getFList());
        mainAddCard = new MainAddCard();
        mainUserCard = new MainUserCard();


        init();
        assemble();
        setAction();
        cardLayout.next(main);
    }





    private void init() {
        setLayout(null);
        setIconImage(Style.Icon.getImage());
        setTitle("KaChat:主界面");
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width-fWidth)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-fHeight)/2,fWidth,fHeight);
        setFocusable(true);
        setUndecorated(true);
        setBackground(Style.nullColor);

        bg1.setBounds(0,0,fWidth,fHeight);
        bg1.setLayout(bgCardLayout);

        bg2.setBounds(0,0,fWidth,fHeight);
        bg2.setLayout(null);

        mainView.setBounds(0,30,800,640);
        mainView.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/main.png", "main", ".png").getAbsolutePath()));
        mainView.setLayout(null);

        menubarView.setBounds(20,0,60,700);
        menubarView.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/menubar.png", "menubar", ".png").getAbsolutePath()));
        menubarView.setLayout(null);

        mbUserIcon.setBounds(5,30,50,50);
        ImageIcon icon =  new ImageIcon(ClientDAO.getUser().getPortrait().getImage());
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        mbUserIcon.setIcon(icon);


        mbUser.setBounds(10,mbUserIcon.getY()+90,100,40);
        mbUser.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/user.png", "user", ".png").getAbsolutePath()));

        mbChat.setBounds(10,mbUser.getY()+70,100,40);
        mbChat.setIcon(mbChatIcon);

        mbGroup.setBounds(10,mbChat.getY()+70,40,40);
        mbGroup.setIcon(mbGroupIcon);

        mbAdd.setBounds(10,mbGroup.getY()+70,40,40);
        mbAdd.setIcon(mbAddIcon);

        mblogo.setBounds(5,620,50,55);
        ImageIcon logoIcon =  new ImageIcon(Style.Icon.getImage());
        logoIcon.setImage(logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        mblogo.setIcon(logoIcon);

        select.setBounds(-10,mbChat.getY()-5,75,65);
        select.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/select.png", "select", ".png").getAbsolutePath()));

        dot.setLocation(999,999);
        dot.setSize(10,10);
        dot.setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/dot.png", "dot", ".png").getAbsolutePath()));

        quit.setBounds(760,45,20,20);
        quit.setFont(new Font("黑体",0,20));
        quit.setForeground(Style.pinColor);

        minimSize.setBounds(735,45,20,20);
        minimSize.setFont(new Font("黑体",0,20));
        minimSize.setForeground(Style.bluColor);
        winMunuBar.setBounds(80,0,720,40);



        main.setBounds(80,0,720,640);
        main.setLayout(cardLayout);


    }
    private void assemble() {

        menubarView.add(mbUserIcon);
        menubarView.add(mbUser);
        menubarView.add(mbChat);
        menubarView.add(mbAdd);
        menubarView.add(mbGroup);
        menubarView.add(mblogo);
        menubarView.add(dot);
        menubarView.add(select);
        main.add(mainUserCard,"user");
        main.add(mainChatCard,"chat");
        main.add(mainAddCard,"add");
        mainView.add(main);
        mainView.add(winMunuBar);

        bg2.add(quit);
        bg2.add(minimSize);
        bg2.add(menubarView);
        bg2.add(mainView);

        bg1.add(bg2);
        add(bg1);

    }
    private void setAction() {
        mbUser.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (select.getY()==mbUser.getY()-5){
                    return;
                }
                Animate.showCard(cardLayout,main,"user",Animate.Type_XY);
                Animate.changeSelect(select,mbUser.getLocation());

                if (dot.getY()==mbUser.getY()){
                    dot.setLocation(999,999);
                }
            }
        });


        mbChat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (select.getY()==mbChat.getY()-5){
                    return;
                }
                Animate.showCard(cardLayout,main,"chat",Animate.Type_XY);
                Animate.changeSelect(select,mbChat.getLocation());


                if (dot.getY()==mbChat.getY()){
                    dot.setLocation(999,999);
                }
            }
        });

        mbAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (select.getY()==mbAdd.getY()-5){
                    return;
                }
                Animate.showCard(cardLayout,main,"add",Animate.Type_XY);
                Animate.changeSelect(select,mbAdd.getLocation());

                if (dot.getY()==mbAdd.getY()){
                    dot.setLocation(999,999);
                }
            }
        });

        mbGroup.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (select.getY()==mbGroup.getY()-5){
                    return;
                }
                Animate.changeSelect(select,mbGroup.getLocation());
                if (dot.getY()==mbGroup.getY()){
                    dot.setLocation(999,999);
                }
            }
        });
        mblogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (select.getY()== mblogo.getY()-5){
                    return;
                }
                Point mblogoP = new Point(mblogo.getX(),mblogo.getY()+5 );
                Animate.changeSelect(select,mblogoP);

            }
        });

        final Point origin = new Point();
        winMunuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();

            }
        });
        winMunuBar.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = getLocation();
                setLocation(point.x+e.getX()- origin.x,point.y+e.getY()- origin.y);
            }
        });

        minimSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientAction.action.miniSize(self,bg1,bgCardLayout, windowPoint,false);
            }
        });

        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClientAction.action.useQuit(self,bg1,bgCardLayout,true);
            }
        });
        addWindowListener(new WindowAdapter() {
            boolean stateSW = true;
            @Override
            public void windowDeiconified(WindowEvent e){
                super.windowIconified(e);
                if (!stateSW){
                    ClientAction.action.miniSize(self,bg1,bgCardLayout,windowPoint,true);
                    stateSW = !stateSW;
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ClientAction.action.useQuit(self,bg1,bgCardLayout,false);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                if (windowPoint.getX()==0){
                    windowPoint.setLocation(self.getX(),self.getY());
                }
                stateSW = !stateSW;
            }
        });

    }

    public MainChatCard getChatView() {
        return mainChatCard;
    }

    public MainAddCard getMainAddCard() {
        return mainAddCard;
    }
}
