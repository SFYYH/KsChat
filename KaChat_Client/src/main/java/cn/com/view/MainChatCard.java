package cn.com.view;

import cn.com.action.ClientAction;
import cn.com.pojo.User;
import cn.com.util.ResourcesUtils;
import cn.com.view.animate.Animate;
import cn.com.view.viewutil.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainChatCard extends JLabel {


    private ImageIcon cancelIcon;
    JTextField searchText;
    JLabel quit;
    JLabel minimSize;
    JLabel name;
    JLabel online;
    public DefaultListModel jListModel;
    public JList<User> jList;
    JScrollPane listJS;
    JLabel cancelButton;
    public CardLayout cardLayout;
    public JLabel chatView;
    List<User> users;

    MainChatCard(List<User> users) {


        this.users = users;
        cancelIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat/cancel.png", "cancel", ".png").getAbsolutePath());
        cancelButton = new JLabel();
        searchText = new JTextField("搜索");
        quit = new QuitLabel();
        minimSize = new MiniSizeLabel();
        name = new JLabel();
        online = new JLabel();
        jListModel = new DefaultListModel<>();
        jList = new JList<User>();
        listJS = new JScrollPane(jList);
        for (User user :
                users) {
            jListModel.addElement(user);
        }
        chatView = new JLabel();
        cardLayout = new CardLayout();
        init();
        assemble();
        setAction();


    }


    private void init() {

        setBounds(0, 0, 700, 620);
        setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat/mianbgview.png", "mianbgview", ".png").getAbsolutePath()));
        setLayout(null);

        cancelButton.setBounds(218, 23, 20, 20);

        listJS.setBorder(Style.nullBorder);
        listJS.setOpaque(false);
        listJS.getViewport().setOpaque(false);
        listJS.setBounds(5, 80, 265, 535);
        listJS.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        listJS.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listJS.getVerticalScrollBar().setUI(new ScrollBarUI());


        jList.setCellRenderer(new FriendsCellRender());
        jList.setOpaque(false);
        jList.setFixedCellHeight(55);
        jList.setModel(jListModel);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flushFrState();

        searchText.setBounds(70, 20, 140, 25);
        searchText.setOpaque(false);
        searchText.setBorder(Style.nullBorder);
        searchText.setFont(new Font("黑体", 0, 12));
        searchText.setSelectionColor(Style.bgColor);

        online.setBounds(325, 22, 20, 20);
        online.setFont(new Font("黑体", 0, 20));

        name.setBounds(355, 20, 250, 25);
        name.setFont(new Font("黑体", 0, 20));


        chatView.setBounds(290, 75, 410, 545);
        chatView.setLayout(cardLayout);


    }

    private void assemble() {

        add(quit);
        add(minimSize);
        add(cancelButton);
        add(listJS);
        add(searchText);
        add(online);
        add(name);
        add(chatView);
    }

    private void setAction() {

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!searchText.getText().equals("")) {
                    searchText.setText("");
                    ClientAction.action.flushFList();
                    cancelButton.setIcon(null);
                }
            }
        });

        searchText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                //冗余代码,但删除后会出问题
                if (searchText.getText().equals("")) {
                    searchText.setText("搜索");
                    ClientAction.action.flushFList();

                }

            }

            @Override
            public void focusGained(FocusEvent e) {
                ClientAction.action.textFocusGained(searchText, "搜索");
                if (!searchText.getText().equals("")) {
                    cancelButton.setIcon(cancelIcon);
                }
                jListModel.removeAllElements();
            }
        });
        searchText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                cancelButton.setIcon(cancelIcon);
                if (searchText.getText().equals("")) {
                    cancelButton.setIcon(null);
                } else {
                    String searchID = searchText.getText();
                    jList.setModel(ClientAction.action.searchByID(searchID));
                }
            }
        });

        jList.addListSelectionListener(new ListSelectionListener() {


            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {
                    User u = flushFrState();
                    if (u != null){
                        Animate.showCard(cardLayout,chatView,u.getID(),Animate.Type_Y);
                    }

                }
            }
        });

    }


    public User flushFrState() {
        User u = jList.getSelectedValue();
        if (u != null) {
            online.setText("●");
            name.setText(u.getName());
            if (u.isOnline()) {
                online.setForeground(Style.greenColor);
            } else {
                online.setForeground(Color.LIGHT_GRAY);
            }

        }
        return u;
    }

}
