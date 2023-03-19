package cn.com.view.viewutil;

import java.awt.*;
import java.util.HashMap;

public class ChatViewLayout implements LayoutManager2 {
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    int componentheight =0;


    HashMap<Component,String> components = new HashMap<Component,String>();
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        String set = (String) constraints;
        components.put(comp,set);

            if (set.equals(LEFT)) {
                comp.setBounds(5, componentheight, 190, 60);
            } else if (set.equals(RIGHT)) {
                comp.setBounds(200, componentheight, 190, 60);
            }
        componentheight = comp.getY()+comp.getHeight();

    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {

    }
}
