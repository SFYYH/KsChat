package cn.com.view.animate;

import cn.com.action.ClientAction;
import cn.com.thread.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class Animate {
    public static final int Type_X = 0;
    public static final int Type_Y = 1;
    public static final int Type_XY = 2;
    public static  void dither (Component component){

       ThreadPool.poolExecutor.execute(new Runnable() {
            synchronized
            @Override
            public void run() {
                synchronized (component){
                    int range = 3;
                    for (int i = 0; i <= 5; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (i%2==1){
                            component.setLocation(component.getX()+range,component.getY()-range);
                        }else {
                            component.setLocation(component.getX()-range,component.getY()+range);
                        }
                    }
                }
            }
        });
    }

    public static void slideXOnL(List<Component> components){

        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (components){
                    while (!ClientAction.action.isNullLV()){
                        try {
                            Thread.sleep(10000);
                            for (int i = 0;i<components.size();i++){
                                Component component = components.get(i);
                                for (int j =0;j<=component.getWidth();j++){
                                    Thread.sleep(1);
                                    component.setLocation(j,component.getY());
                                    component.revalidate();
                                }
                                Thread.sleep(10000);
                            }

                            for (int i = components.size()-1;i>=0;i--){
                                Component component = components.get(i);
                                for (int j =component.getWidth();j>=0;j--){
                                    Thread.sleep(1);
                                    component.setLocation(j,component.getY());
                                }
                                Thread.sleep(10000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    public static void slideY(Component self, Point target){
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (self){
                    double selfY =  self.getLocation().getY();
                    double targetY =target.getLocation().getY()-5;

                    if (selfY==targetY){
                        return;
                    }
                    double deviation = (targetY-selfY)/50f;
                    Point p = new Point(self.getLocation());
                    for (double i = selfY;p.getY()!=targetY;i+=deviation){
                        p.setLocation(p.getX(),i);
                        self.setLocation(p);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

    }
    public static void surfaceOut(Window window){
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (window){
                    Point p =  window.getLocation();
                    for (float i = 1f;i>=0f;i-=0.04f){
                        p.setLocation(p.getX(),p.getY()+1f);
                        window.setLocation(p);
                        window.setOpacity( i);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    window.setOpacity(0f);
                    window.setVisible(false);
                }
            }
        });
    }
    public static void surfaceIn(Window window){
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (window){
                    window.setOpacity(0f);
                    Point p =  window.getLocation();
                    window.setVisible(true);
                    for (float i = 0f;i<=1f;i+=0.04f){
                        p.setLocation(p.getX(),p.getY()-1f);
                        window.setLocation(p);
                        window.setOpacity(i);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    window.setOpacity(1f);
                }
            }
        });
    }
    public static void changeSelect(Component self,Point target){
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (self){
                    int selfX = self.getX();
                    if (self.getY()==target.getY()-5){
                        return;
                    }
                    float distance = selfX+self.getWidth();
                    distance/=100;
                    Point p = new Point(self.getLocation());
                    while (self.getX()+self.getWidth()>0){
                        p.setLocation(p.getX()-distance,p.getY());
                        self.setLocation(p);
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    p.setLocation(p.getX(),target.getY()-5);
                    while (self.getX()<selfX){
                        p.setLocation(p.getX()+distance,p.getY());
                        self.setLocation(p);
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    p.setLocation(selfX,p.getY());
                    self.setLocation(p);
                }
            }
        });
    }


    public static  void showCard(CardLayout cardLayout, Container card, String name,int type) {
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public  void run() {
                synchronized (card){
                    int width= card.getWidth();
                    int height = card.getHeight();
                    int hgap = cardLayout.getHgap();
                    int vgap = cardLayout.getVgap();
                if (type == Type_XY){
                    while (cardLayout.getHgap()<width/2||cardLayout.getVgap()<height/2){
                    cardLayout.setHgap(cardLayout.getHgap()+5);
                    cardLayout.setVgap(cardLayout.getVgap()+5);
                    card.revalidate();
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    }
                    cardLayout.show(card,name);
                    while (cardLayout.getHgap()>hgap&&cardLayout.getVgap()>vgap){
                        cardLayout.setHgap(cardLayout.getHgap()-5);
                        cardLayout.setVgap(cardLayout.getVgap()-5);
                        card.revalidate();
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else if (type== Type_X){
                    while (cardLayout.getHgap()<width/2){
                    cardLayout.setHgap(cardLayout.getHgap()+5);

                    card.revalidate();
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    cardLayout.show(card,name);
                    while (cardLayout.getHgap()>hgap){
                        cardLayout.setHgap(cardLayout.getHgap()-5);

                        card.revalidate();
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else if (type== Type_Y){
                    while (cardLayout.getVgap()<height/2){
                        cardLayout.setVgap(cardLayout.getVgap()+5);
                        card.revalidate();
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                        cardLayout.show(card,name);

                        while (cardLayout.getVgap()>vgap){
                            cardLayout.setVgap(cardLayout.getVgap()-5);
                            card.revalidate();
                            try {
                                Thread.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
    public static  void vanishInPoint(Frame jFrame,JLabel jLabel,CardLayout cardLayout, Point toPoint){
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (jFrame){

                    Point p = jFrame.getLocation();
                    Dimension d = jFrame.getSize();
                    double speed = 0.05f;
                    double scaleHgap = d.getWidth()/ (4/speed);
                    double scaleVgap = d.getHeight()/(4/speed);
                    double scaleX = (toPoint.getX()-p.getX())/ (2/speed);
                    double scaleY =(toPoint.getY()-p.getY())/ (2/speed);
                    for (float i = 1f;i>0f;i-=speed){
                        p.setLocation(p.getX()+scaleX,p.getY()+scaleY);
                        cardLayout.setHgap((int) (cardLayout.getHgap()+scaleHgap));
                        cardLayout.setVgap((int) (cardLayout.getVgap()+scaleVgap));
                        jLabel.setLayout(cardLayout);
                        jFrame.setLocation(p);
                        jFrame.setOpacity(i);
                        jLabel.revalidate();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    cardLayout.setHgap(jFrame.getHeight()/2);
                    cardLayout.setVgap(jFrame.getWidth()/2);
                    jLabel.setLayout(cardLayout);
                    jFrame.setLocation(toPoint);
                    jFrame.setOpacity(0);
                    jLabel.revalidate();;
                }
            }
        });

    }
    public static  void emergedInPoint(Frame jFrame, JLabel jLabel, CardLayout cardLayout, Point toPoint){
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (jFrame){
                    if (cardLayout.getVgap()!=jFrame.getWidth()/2){
                        cardLayout.setHgap(jFrame.getHeight()/2);
                        cardLayout.setVgap(jFrame.getWidth()/2);
                        jLabel.setLayout(cardLayout);
                        jFrame.setOpacity(0);
                        jLabel.revalidate();;
                    }
                    Point p = jFrame.getLocation();
                    double speed = 0.02f;
                    double scaleHgap = cardLayout.getHgap()/ (1/speed);
                    double scaleVgap = cardLayout.getVgap()/(1/speed);
                    double scaleX = (toPoint.getX()-p.getX())/ (1/speed);
                    double scaleY =(toPoint.getY()-p.getY())/ (1/speed);
                    for (float i = 0f;i<=1f;i+=speed){
                        p.setLocation(p.getX()+scaleX,p.getY()+scaleY);
                        cardLayout.setHgap((int) (cardLayout.getHgap()-scaleHgap));
                        cardLayout.setVgap((int) (cardLayout.getVgap()-scaleVgap));
                        jLabel.setLayout(cardLayout);
                        jFrame.setLocation(p);
                        jFrame.setOpacity(i);
                        jLabel.revalidate();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    cardLayout.setHgap(0);
                    cardLayout.setVgap(0);
                    jLabel.setLayout(cardLayout);
                    jFrame.setLocation(toPoint);
                    jFrame.setOpacity(1);
                    jLabel.revalidate();

                }
            }
        });

    }

    public static void slideOut_Box(Box vB, Container self,int type) {
        ThreadPool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (self){
                    Point p = self.getLocation();
                    Dimension d = self.getSize();
                    if (type == Type_Y){
                        double scale = d.getHeight()/100f;
                        while (d.getHeight()>0){
                            d.setSize(d.getWidth(),d.getHeight()-scale);
                            self.setSize(d);
                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }else if (type == Type_X){
                        double scale = d.getWidth()/100f;

                        while (p.getX()<=self.getWidth()){
                            p.setLocation(p.getX()+scale,p.getY());
                            self.setLocation(p);
                            try {
                                Thread.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    vB.remove(self);
                    vB.revalidate();
                }
            }
        });

    }


}
