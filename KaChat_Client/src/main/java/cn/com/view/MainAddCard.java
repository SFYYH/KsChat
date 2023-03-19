package cn.com.view;

import cn.com.action.ClientAction;
import cn.com.util.ResourcesUtils;
import cn.com.view.viewutil.MiniSizeLabel;
import cn.com.view.viewutil.QuitLabel;
import cn.com.view.viewutil.ScrollBarUI;
import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainAddCard extends JLabel {
    private ImageIcon cancelIcon;
    JTextField searchText;
    private ImageIcon addButtonOut;
    private ImageIcon addButtonC;
    private ImageIcon addButtonIn;
    public JLabel msg;
    JLabel addButton;
    JLabel quit;
    JLabel minimSize;
    public Box vB;
    public JPanel jPanel;

    JScrollPane jScroll;
    JLabel cancelButton;

    MainAddCard() {
        cancelIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat/cancel.png", "cancel", ".png").getAbsolutePath());

        quit = new QuitLabel();
        minimSize = new MiniSizeLabel();
        cancelButton = new JLabel();
        searchText = new JTextField("请输入需要添加好友的UID");

        msg = new JLabel();
        jPanel = new JPanel();
        jScroll = new JScrollPane(jPanel);
        vB = Box.createVerticalBox();
        addButtonOut = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/addButtonout.png", "addButtonout", ".png").getAbsolutePath());
        addButtonC = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/addButtonC.png", "addButtonC", ".png").getAbsolutePath());
        addButtonIn = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/addButtonin.png", "addButtonin", ".png").getAbsolutePath());
        addButton = new JLabel("添加");
        init();
        assemble();
        setAction();
    }


    private void init() {
        setLayout(null);
        setBounds(0, 0, 700, 620);
        setIcon(new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/add/addbg.png", "addbg", ".png").getAbsolutePath()));

        searchText.setBounds(220, 25, 180, 30);
        searchText.setOpaque(false);
        searchText.setBorder(Style.nullBorder);
        searchText.setFont(new Font("黑体", 0, 15));
        searchText.setSelectionColor(Style.bgColor);
        searchText.setHorizontalAlignment(JTextField.CENTER);

        cancelButton.setBounds(405, 30, 20, 20);

        msg.setBounds(235, 0, 180, 25);
        msg.setForeground(Style.pinColor);
        msg.setHorizontalTextPosition(SwingConstants.CENTER);

        addButton.setBounds(465, 25, 60, 30);
        addButton.setIcon(addButtonOut);
        addButton.setFont(new Font("黑体", Font.PLAIN, 17));
        addButton.setForeground(Color.white);
        addButton.setHorizontalTextPosition(SwingConstants.CENTER);

        jScroll.setBorder(Style.nullBorder);
        jScroll.setOpaque(false);
        jScroll.getViewport().setOpaque(false);
        jScroll.setBounds(5, 80, 693, 530);
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScroll.getVerticalScrollBar().setUI(new ScrollBarUI());


        jPanel.setBackground(new Color(0xdcdcdc));


    }

    private void assemble() {


        jPanel.add(vB);
        add(quit);
        add(minimSize);
        add(msg);
        add(cancelButton);
        add(searchText);
        add(addButton);
        add(jScroll);
    }

    private void setAction() {
        searchText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ClientAction.action.textFocusGained(searchText, "请输入需要添加好友的UID");
                if (!searchText.getText().equals("")) {
                    cancelButton.setIcon(cancelIcon);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                ClientAction.action.textFocusLost(searchText, "请输入需要添加好友的UID");

            }
        });

        searchText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                cancelButton.setIcon(cancelIcon);
                if (searchText.getText().equals("")) {
                    cancelButton.setIcon(null);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ClientAction.action.addUserNotice(searchText.getText());
                }
            }
        });


        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!searchText.getText().equals("")) {
                    searchText.setText("");
                    msg.setText("");
                    cancelButton.setIcon(null);
                }
            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addButton.setIcon(addButtonC);
                ClientAction.action.addUserNotice(searchText.getText());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                addButton.setIcon(addButtonOut);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setIcon(addButtonOut);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setIcon(addButtonIn);
            }
        });

    }

}
