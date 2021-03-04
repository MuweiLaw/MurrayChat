package com.murray.utils;

import java.awt.*;

/**
 * @author Murray Law
 * @describe 布格袋布局工具包
 * @createTime 2020/12/12
 */
public class GridBagUtil {
    private static final GridBagConstraints wrap = new GridBagConstraints();
    private static final GridBagConstraints wrapBoth = new GridBagConstraints();
    private static final GridBagConstraints msgWrap = new GridBagConstraints();
    private static final GridBagLayout gridBagLayout = new GridBagLayout();
    private static final Insets insets = new Insets(10, 0, 10, 0);

    public static GridBagConstraints wrap() {
        //布格袋布局换行
        wrap.gridwidth = GridBagConstraints.REMAINDER;
        wrap.fill = GridBagConstraints.NONE;
        return wrap;
    }
    public static GridBagConstraints wrapBoth() {
        //布格袋布局换行
        wrapBoth.gridwidth = GridBagConstraints.REMAINDER;
        wrapBoth.fill = GridBagConstraints.BOTH;
        return wrapBoth;
    }
    public static GridBagConstraints msgWrap() {
        msgWrap.insets = insets;
        //布格袋布局换行
        msgWrap.gridwidth = GridBagConstraints.REMAINDER;
        msgWrap.fill = GridBagConstraints.NONE;
        return msgWrap;
    }

    public static GridBagLayout getGridBagLayout() {
        return gridBagLayout;
    }
}
