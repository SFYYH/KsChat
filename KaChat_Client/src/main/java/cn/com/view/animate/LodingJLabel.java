package cn.com.view.animate;

import cn.com.view.viewutil.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LodingJLabel extends JLabel {

    private static final long serialVersionUID = 1551571546L;

    private Timer timer;
    private int delay;
    private int startAngle;
    private int arcAngle = 0;
    private int orientation;
    private int bw;
    private int p;

    public static final int CLOCKWISE = 0;
    public static final int ANTICLOCKWISE = 1;

    public LodingJLabel() {
        p = 0;
        this.delay = 5;
        this.orientation = CLOCKWISE;
        init();
    }

    public LodingJLabel(int delay) {
        p = 0;
        this.delay = delay;
        this.orientation = CLOCKWISE;
        init();
    }

    public LodingJLabel(int delay, int orientation,int bw) {
        p = 0;
        this.delay = delay;
        this.orientation = orientation;
        this.bw = bw;
        init();
    }

    @Override
    public void show() {
        p = 0;
        this.timer.start();
    }
    public void stop() {
        p = bw;
        repaint();
        this.timer.stop();
    }
    /**
     * @param orientation	set the direction of rotation
     *
     * @beaninfo
     *        enum: CLOCKWISE LodingPanel.CLOCKWISE
     *        		ANTICLOCKWISE LodingPanel.ANTICLOCKWISE
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private void init() {
        this.timer = new Timer(delay, new ReboundListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawArc(g);
    }

    private void drawArc(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        //抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        //设置画笔颜色

        g2d.setColor(Style.pinColor);
        g2d.fillArc(p, p, width-p*2, height-p*2, startAngle, arcAngle);
        g2d.setColor(Style.nullColor);
        g2d.fillArc(bw, bw, width-bw*2, height-bw*2, 0, 360);
        g2d.dispose();
    }

    private class ReboundListener implements ActionListener {

        private int o = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (startAngle < 360) {
                //控制每个DELAY周期旋转的角度，+ 为逆时针  - 为顺时针
                switch (orientation) {
                    case ANTICLOCKWISE:
                        startAngle = startAngle - 1;
                        break;
                    case CLOCKWISE:
                    default:
                        startAngle = startAngle + 1;
                        break;
                }
            } else {
                startAngle = 0;
            }

            if (o == 0) {
                if (arcAngle >= 355) {
                    o = 1;
                    orientation = ANTICLOCKWISE;
                }else {
                    if (orientation == CLOCKWISE) {
                        arcAngle += 1;
                    }
                }
            }else {
                if (arcAngle <= 1) {
                    o = 0;
                    orientation = CLOCKWISE;
                }else {
                    if (orientation == ANTICLOCKWISE) {
                        arcAngle -= 1;
                    }
                }
            }

            repaint();
        }
    }
}
