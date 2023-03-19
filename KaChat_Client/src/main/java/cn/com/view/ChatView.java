package cn.com.view;

import cn.com.action.ClientAction;
import cn.com.util.ResourcesUtils;
import cn.com.view.viewutil.ScrollBarUI;
import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatView extends JLabel {

    private ChatView self;
    public JPanel chatOut;
    public String toID;

    BoxLayout boxLayout;
    JScrollPane outJS ;
    JScrollPane inJS ;
    public JTextArea chatIn ;
    JLabel sendButton;
    private ImageIcon sendButtonOut;
    private ImageIcon sendButtonC;
    private ImageIcon sendButtonIn;
    JLabel sendText;
    JLabel setSizeLabel;
    ScrollBarUI scrollBarUIOfOut;
    ScrollBarUI scrollBarUIOfIn;
    public JScrollBar jScrollBarOfOut;
    public JScrollBar jScrollBarOfIn;
    public ChatView(String toID){
        self = this;
        this.toID = toID;
        chatOut = new JPanel();


        boxLayout = new BoxLayout(chatOut, BoxLayout.Y_AXIS);
        outJS = new JScrollPane(chatOut);
        scrollBarUIOfOut = new ScrollBarUI();
        scrollBarUIOfIn = new ScrollBarUI();

        chatIn = new JTextArea();
        inJS = new JScrollPane(chatIn);
        sendButton = new JLabel();
        sendButtonOut = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat/sendbutton.png", "sendbutton", ".png").getAbsolutePath());
        sendButtonC = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat/sendbuttonc.png", "sendbuttonc", ".png").getAbsolutePath());
        sendButtonIn = new ImageIcon(ResourcesUtils.getResource("/view/icon/mainview/chat/sendbuttonin.png", "sendbuttonin", ".png").getAbsolutePath());
        sendText = new JLabel();
        setSizeLabel = new JLabel();
        init();
        assemble();
        setAction();
    }




    private void init() {

        chatOut.setSize(400,380);
        chatOut.setBackground(new Color(0xeeeeee));
        chatOut.setLayout(boxLayout);

      //
        outJS.setBounds(5,5,410,380);
        outJS.setBorder(Style.nullBorder);
        outJS.setOpaque(false);
        outJS.getViewport().setOpaque(false);
        outJS.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outJS.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollBarOfOut = outJS.getVerticalScrollBar();
        jScrollBarOfOut.setUnitIncrement(40);

        jScrollBarOfOut.setUI(scrollBarUIOfOut);

        inJS.setBounds(5,390,410,115);
        inJS.setBorder(Style.nullBorder);
        inJS.setOpaque(false);
        inJS.getViewport().setOpaque(false);
        inJS.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inJS.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollBarOfIn = inJS.getVerticalScrollBar();
        jScrollBarOfIn.setUnitIncrement(40);
        jScrollBarOfIn.setUI(scrollBarUIOfIn);


        chatIn.setSize(400,115);
        chatIn.setBackground(new Color(0xeeeeee));
        chatIn.setLineWrap(true);
        chatIn.setWrapStyleWord(true);
        chatIn.setFont(new Font("黑体",0,20));

        sendButton.setBounds(325,510,80,30);
        sendButton.setIcon(sendButtonOut);


        sendText.setBounds(325,510,80,30);
        sendText.setHorizontalAlignment(JTextField.CENTER);
        sendText.setForeground(Color.white);
        sendText.setFont(new Font("黑体",0,17));
        sendText.setText("发送");

        setSizeLabel.setBounds(5,385,400,5);
    }
    private void assemble() {

        add(outJS);
        add(setSizeLabel);
        add(sendText);
        add(inJS);
        add(sendButton);

    }
    private void setAction() {

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                sendButton.setIcon(sendButtonC);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                sendButton.setIcon(sendButtonIn);
                if (chatIn.getText().equals("")){
                    return;
                }
                ClientAction.action.sendMsg(self);
                jScrollBarOfOut.setValue(jScrollBarOfOut.getMaximum());


            }


            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setIcon(sendButtonIn);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setIcon(sendButtonOut);
            }
        });

        chatIn.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                chatIn.setBackground(Color.white);
            }

            @Override
            public void focusLost(FocusEvent e) {
                chatIn.setBackground(new Color(0xeeeeee));
            }
        });

        chatIn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {


                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    chatIn.append("\n");
                }else if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    e.consume();
                    if (!chatIn.getText().equals("")){
                        ClientAction.action.sendMsg(self);
                        jScrollBarOfOut.setValue(jScrollBarOfOut.getMaximum());
                    }
                }

            }
        });

        final int[] o = new int[1];
        setSizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                o[0] = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int size;

                if(inJS.getHeight()<=30){
                    chatOut.setSize(400,455 );
                    outJS.setSize(410,455 );
                    setSizeLabel.setLocation(5, 460);
                    chatIn.setSize(400,40 );
                    inJS.setBounds(5,465, 410,40 );
                } else if(inJS.getHeight()>=465){
                    chatOut.setSize(400,40 );
                    outJS.setSize(410,40 );
                    setSizeLabel.setLocation(5, 45);
                    chatIn.setSize( 400,455 );
                    inJS.setBounds(5,50, 410,455 );
                }
            }

        });
        setSizeLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if(inJS.getHeight()<=470&&inJS.getHeight()>=30){
                    int pDragged = e.getY() - o[0];
                    chatOut.setSize(400,chatOut.getHeight() + pDragged );
                    outJS.setSize(410,outJS.getHeight() + pDragged );
                    setSizeLabel.setLocation(5, setSizeLabel.getY() + pDragged);
                    chatIn.setBounds(0,chatIn.getY() + pDragged, 400,chatIn.getHeight() - pDragged );
                    inJS.setBounds(5,inJS.getY() + pDragged, 410,inJS.getHeight() - pDragged );
                }
                jScrollBarOfOut.setValue(jScrollBarOfOut.getMaximum());
                jScrollBarOfIn.setValue(jScrollBarOfIn.getMaximum());
            }
        });
    }

}
