package cn.com.action;

import cn.com.dao.ClientDAO;
import cn.com.pojo.Message;
import cn.com.pojo.MessageType;
import cn.com.pojo.User;
import cn.com.thread.ClientListener;
import cn.com.thread.ThreadPool;
import cn.com.util.RoundHeadImgUtils;
import cn.com.view.*;
import cn.com.view.animate.Animate;
import cn.com.view.viewutil.Style;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class ClientAction {
    private LoginView loginView;
    private MainView mainView;
    private ClientListener clientListener;
    public HashMap<String, ChatView> chatCards;

    public static ClientAction action;

    private MainChatCard mainChatCard;
    private MainAddCard mainAddCard;

    public ClientAction() {
        action = this;
        init();
    }

    private void init() {
        System.setProperty("sun.java2d.noddraw", "true");
        loginView = new LoginView();
        Animate.surfaceIn(loginView);
        Animate.slideXOnL(loginView.pictures);
//        clientListener = new ClientListener("124.222.55.142",7758);
        clientListener = new ClientListener("localhost", 7758);
//        113.245.83.186
//        clientListener = new ClientListener("169.254.183.20",7758);

        ThreadPool.poolExecutor.execute(clientListener);

    }
    public void connectMSG(boolean isConnect){
        if (isConnect){
            loginView.getMsg().setText("");
            loginView.lodingJLabel.stop();
            loginView.buttonClickL.setText("登录");
        }else {
            loginView.getMsg().setText("无法连接至服务器,正在尝试重连...");
            loginView.lodingJLabel.show();
            loginView.buttonClickL.setText("登录");
        }


    }



    public void login() {

        if (clientListener == null || !clientListener.isConnect()) {
            connectMSG(false);
            Animate.dither(loginView.buttonL);
            Animate.dither(loginView.msg);
            return;
        }else if (!loginView.uIDText.getText().matches("^\\d{6,}$")
                ||loginView.pwgText.getText().equals("")
                ||loginView.pwgText.getText().equals("请输入密码")){

            loginOK("null", null);
            return;
        }
        clientListener.send(new Message(MessageType.JOIN, loginView.uIDText.getText(), null, loginView.pwgText.getText()));
    }

    public void loginOK(String ok, User data) {
        if (loginView == null) {
            return;
        }
        if (ok.equals("OK")) {
            loginView.buttonClickL.setText("登录成功√");
            upData(data);
            creaMainView();
            return;
        } else if (ok.equals("isOnline")) {
            loginView.msg.setText("该UID已被登录");
        } else {
            loginView.msg.setText("UID不存在或密码错误");
        }
        loginView.lodingJLabel.stop();
        Animate.dither(loginView.buttonL);
        loginView.buttonClickL.setText("登录");
        Animate.dither(loginView.msg);

    }

    private void creaMainView() {
        chatCards = new HashMap<String, ChatView>();
        Animate.surfaceOut(loginView);
        mainView = new MainView();
        mainChatCard = mainView.getChatView();
        mainAddCard = mainView.getMainAddCard();
        flushChatCard();
        mainChatCard.jList.setSelectedIndex(0);
        mainChatCard.flushFrState();
        Animate.surfaceIn(mainView);
        mainChatCard.cardLayout.first(mainChatCard.chatView);
        loginView = null;
    }


    public void upData(User u) {
        ClientDAO.upData(u);
    }


    public void sendMsg(ChatView chatView) {
        ChatMessage selfMsg = new ChatMessage(ClientDAO.getUser().getPortrait(), chatView.chatIn.getText(), true);
        chatView.chatOut.add(selfMsg);

        clientListener.send(new Message(MessageType.MESSAGE, ClientDAO.getUser().getID(), chatView.toID, chatView.chatIn.getText()));

        ClientDAO.getFriend(chatView.toID).setMotto(chatView.chatIn.getText());
        flushUserInFList(chatView.toID, true);
        chatView.chatIn.setText("");
        chatView.chatOut.revalidate();
        chatView.jScrollBarOfOut.setValue(chatView.jScrollBarOfOut.getMaximum());
    }

    public void receiveMsg(String fromID, String msg) {
        ClientDAO.getFriend(fromID).setMotto(msg);
        ChatView chatView = chatCards.get(fromID);
        flushUserInFList(fromID, true);
        chatView.chatOut.add(new ChatMessage(ClientDAO.getFriend(fromID).getPortrait(), msg, false));
        chatView.jScrollBarOfOut.setValue(chatView.jScrollBarOfOut.getMaximum());
        if (mainView.select.getY() != mainView.mbChat.getY() - 5) {
            mainView.dot.setLocation(40, mainView.mbChat.getY());
        }
    }


    public void flushChatCard() {
        for (User user : ClientDAO.getFList()) {
            if (!chatCards.containsKey(user.getID())) {
                ChatView chatCard = new ChatView(user.getID());
                chatCards.put(user.getID(), chatCard);
                mainChatCard.chatView.add(chatCard, user.getID());
            }
        }
    }


    public void fdState(String fdID) {
        User u = ClientDAO.getFriend(fdID);
        u.setOnline(!u.isOnline());
        flushUserInFList(fdID, false);
        mainChatCard.flushFrState();
    }


    public int flushFList() {
        int index = mainChatCard.jList.getSelectedIndex();
        if (!mainChatCard.jListModel.isEmpty()) {
            mainChatCard.jListModel.removeAllElements();
        }
        for (User user : ClientDAO.getFList()) {
            mainChatCard.jListModel.addElement(user);
        }
        mainChatCard.jList.setModel(mainChatCard.jListModel);
        return index;
    }

    public void flushUserInFList(String uID, boolean isMSG) {
        User user = ClientDAO.getFriend(uID);
        int index = mainChatCard.jListModel.indexOf(user);
        int selectedIndex = mainChatCard.jList.getSelectedIndex();
        mainChatCard.jListModel.removeElement(user);
        if (isMSG) {
            mainChatCard.jListModel.add(0, user);
        } else {
            mainChatCard.jListModel.add(index, user);
        }

        mainChatCard.jList.setModel(mainChatCard.jListModel);
        if (selectedIndex == index && isMSG) {
            mainChatCard.jList.setSelectedIndex(0);
        } else {
            if (selectedIndex >= index) {
                mainChatCard.jList.setSelectedIndex(selectedIndex);
            } else {
                mainChatCard.jList.setSelectedIndex(selectedIndex + 1);
            }

        }
    }

    public boolean textFocusLost(JTextField textField, String text) {
        if (textField.getText().equals("")) {
            textField.setText(text);
            return true;
        }
        return false;
    }

    public boolean textFocusGained(JTextField textField, String text) {

        if (textField.getText().equals(text)) {
            textField.setText("");
            return false;
        }
        return true;
    }

    public boolean pwFocusGained(JPasswordField pwText, String text) {
        if (pwText.getText().equals(text)) {
            pwText.setText("");
            pwText.setEchoChar('●');
            return false;
        }
        return true;
    }

    public boolean pwFocusLost(JPasswordField pwText, String text) {
        if (pwText.getText().equals("")) {
            pwText.setEchoChar((char) 0);
            pwText.setText(text);
            return true;
        }
        return false;
    }

    public boolean isNullLV() {
        return loginView == null;
    }

    public boolean validate(String text, String regular) {
        if (!Pattern.matches(regular, text) && !text.equals("")) {
            return false;
        }
        return true;
    }

    public ImageIcon loadUserIcon(JLabel userIcon) {
        userIcon.setText("");
        FileDialog load = new FileDialog(loginView, "选择头像", FileDialog.LOAD);
        load.setVisible(true);
        ImageIcon userImage = null;
        try {
            if (!load.getFile().matches("^.+\\.(?i)(png|jpg)$")) {
                userIcon.setForeground(Style.pinColor);
                userIcon.setText("只能为JPG或PNG文件");
                Animate.dither(userIcon);
                return null;
            }
            String iconPath = load.getDirectory() + load.getFile();
            BufferedImage image = ImageIO.read(new File(iconPath));
            BufferedImage rounded = RoundHeadImgUtils.makeRoundedCorner1(image, 3000);
            userImage = new ImageIcon(rounded);
            userImage.setImage(userImage.getImage().getScaledInstance(120, 120, Image.SCALE_AREA_AVERAGING));
            userIcon.setIcon(userImage);
        } catch (NullPointerException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userImage;
    }

    public void sendRegisterNotice(String userName, char[] password, ImageIcon userImageIcon) {
        if(!clientListener.isConnect()){
            Animate.dither(loginView.register.buttonL);
            loginView.register.buttonL.setText("注册");
            Animate.dither(loginView.msg);
        }
        User u = new User();
        u.setPortrait(userImageIcon);
        u.setFriends(new ArrayList<User>());
        u.setName(userName);
        u.setPassword(new String(password));

        clientListener.send(new Message(MessageType.NOTICE, "register", null, u));

    }

    public void registerOK(String toID) {
        Register register = loginView.register;
        register.uIDText.setText("您的UID为:" + toID);
        register.buttonL.setText("注册成功√");
        register.buttonL.setIcon(register.yIcon);
        loginView.userIconL.setIcon(register.userIcon.getIcon());
    }

    public ListModel searchByID(String searchID) {

        return ClientDAO.searchFList(searchID);
    }

    public void addUserNotice(String addID) {
        User user = new User();
        user.setID(addID);
        if (!addID.matches("^[0-9]{6}$") ||
                ClientDAO.getFList().contains(user) ||
                addID.equals(ClientDAO.getUser().getID())) {
            mainAddCard.msg.setText("UID输入有误,或已经是好友");
            Animate.dither(mainAddCard.msg);
            return;
        }

        clientListener.send(new Message(MessageType.NOTICE, "addUser", addID, ClientDAO.getUser().getID()));

        mainAddCard.msg.setText("    好友请求已发送√");

    }

    public void addUserNotice(User addUser) {
        mainAddCard.vB.add(new AddCard(addUser));
        mainAddCard.vB.revalidate();
        if (mainView.select.getY() != mainView.mbAdd.getY() - 5) {
            mainView.dot.setLocation(40, mainView.mbAdd.getY());
        }
    }


    public void addAccept(AddCard self, String uID) {
        clientListener.send(new Message(MessageType.NOTICE,"addAccept",uID,ClientDAO.getUser().getID()));
        Animate.slideOut_Box(mainAddCard.vB, self, Animate.Type_Y);
    }

    public void addRejected(AddCard self) {
        Animate.slideOut_Box(mainAddCard.vB, self, Animate.Type_X);
    }

    public void upDataByUser(String name, String password, ImageIcon userImage) {
        User user = ClientDAO.getUser();
        user.setName(name);
        user.setPortrait(userImage);
        ImageIcon imageIcon = new ImageIcon(user.getPortrait().getImage());
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        mainView.mbUserIcon.setIcon(imageIcon);

        clientListener.send(new Message(MessageType.NOTICE, "upDataByUser", password, ClientDAO.getUser()));
    }

    public void upDataByUserOK(User upDate) {
        User srcUser = ClientDAO.getFriend(upDate.getID());
        srcUser.setName(upDate.getName());
        srcUser.setPortrait(upDate.getPortrait());
        flushUserInFList(upDate.getID(), false);
    }

    public void addUserOK(User upData) {
        ClientDAO.getFList().add(upData);
        flushChatCard();
        int selectedIndex = mainChatCard.jList.getSelectedIndex();
        mainChatCard.jListModel.addElement(upData);
        mainChatCard.jList.setModel(mainChatCard.jListModel);
        mainChatCard.jList.setSelectedIndex(selectedIndex);
        mainChatCard.flushFrState();
    }

    public void useQuit(Frame self,JLabel jLabel,CardLayout cardLayout,boolean isClick) {
        if (isClick){
            Point sp = new Point(self.getX()+self.getWidth()/2,self.getY()-self.getHeight()/2);
            Animate.vanishInPoint(self,jLabel,cardLayout,sp);
        }else {
            Animate.surfaceOut(self);
        }
        clientListener.stopHeartBeat();
        if (ClientDAO.getUser()==null){
            clientListener.send(new Message(MessageType.QUIT,null, null,null));
        }else {
            clientListener.send(new Message(MessageType.QUIT,ClientDAO.getUser().getID(), null,null));
        }

        clientListener.closeSocket();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    public void miniSize(Frame self, JLabel jLabel, CardLayout cardLayout, Point windowPoint, boolean sw) {
        Point midpoint = new Point((Toolkit.getDefaultToolkit().getScreenSize().width-self.getWidth())/2, Toolkit.getDefaultToolkit().getScreenSize().height);

        if (sw){
            self.setLocation(midpoint);
            Animate.emergedInPoint(self,jLabel,cardLayout,windowPoint);
        }else {
            windowPoint.setLocation(self.getLocation());

            Animate.vanishInPoint(self,jLabel,cardLayout,midpoint);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            self.setExtendedState(Frame.ICONIFIED);
        }

    }


}
