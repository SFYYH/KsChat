package cn.com.view.viewutil;

import cn.com.util.ResourcesUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Style {

    public static final Color nullColor = new Color(255, 255, 255, 0);
    public static final Color bgColor = new Color(0xdcdcdc);
    public static final MatteBorder nullBorder = new MatteBorder(0, 0, 0, 0, nullColor);
    public static final Color bluColor = (new Color(0x448aca));
    public static final Color yelColor = (new Color(0xf29a76));
    public static final Color pinColor = (new Color(0xeb6877));
    public static final Color greenColor = (new Color(0x80c269));
    public static final Font textFont = new Font("黑体", Font.CENTER_BASELINE, 25);

    public static final ImageIcon addIcon = new ImageIcon(ResourcesUtils.getResource("/view/icon/registerview/defaultIcon.png", "defaultIcon", ".png").getAbsolutePath());
    public static final ImageIcon Icon = new ImageIcon(ResourcesUtils.getResource("/view/icon/loginview/kachaticon.png", "kachaticon", ".png").getAbsolutePath());
}
