package com.murray.view.vo.cell;

import javax.swing.*;

/**
 * @author Murray Law
 * @describe 自定义的JLabel
 * @createTime 2020/11/23
 */
public class MyLabel extends JLabel {
    private  String attribute;
    public MyLabel(String text,String attribute,int align) {
        super(text,align);
        this.attribute=attribute;
    }
    public MyLabel(String text,String attribute) {
        super(text);
        this.attribute=attribute;
    }
    public String getAttribute(){
        return attribute;
    }
}
