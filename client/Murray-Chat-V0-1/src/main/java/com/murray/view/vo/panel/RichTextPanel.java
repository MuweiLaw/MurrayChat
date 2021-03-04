package com.murray.view.vo.panel;

import com.murray.handler.listener.DropTargetListenerImpl;
import com.murray.view.vo.cell.MailUserLabel;
import com.murray.view.vo.cell.MyFileJPanel;
import com.murray.view.vo.cell.MyLabel;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.murray.utils.FontUtil.MICROSOFT_YA_HEI_16;
import static com.murray.utils.PictureManipulationUtil.getImageIcon;

/**
 * @author Murray Law
 * @describe 富文本框J, 封装TextPanel
 * @createTime 2020/11/12
 */
public class RichTextPanel extends JScrollPane {

    private JTextPane textPane;
    private DropTargetListenerImpl listener;

    public RichTextPanel() {
        textPane = new JTextPane();
        // 在 textArea 上注册拖拽目标监听器
        setViewportView(textPane);
        //setBorder(null);
        //setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    }

    /**
     * @param fatherPanel
     * @param enabledBtn  设置是否可用
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     * @description 拖拽实现构造
     * @author Murray Law
     * @date 2020/11/23 14:26
     */
    public RichTextPanel(JPanel fatherPanel, JButton enabledBtn, int x, int y, int width, int height) {
        setBorder(null);
        textPane = new JTextPane();
        textPane.setBorder(null);
        textPane.setFont(MICROSOFT_YA_HEI_16);
        // 创建拖拽目标监听器
        listener = new DropTargetListenerImpl(textPane, enabledBtn);

        // 注册拖拽目标监听器
        DropTarget dropTarget = new DropTarget(textPane, DnDConstants.ACTION_COPY_OR_MOVE, listener, true);
        setViewportView(textPane);
        setBounds(x, y, width, height);
        fatherPanel.add(this);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public void insertIcon(File file) {
        textPane.insertIcon(getImageIcon(file.getPath(), 150, 90)); // 插入图片
    }

    public void insertComponent(Component component) {
        textPane.insertComponent(component); // 插入图片

    }

    public String getPaneText() {
        return textPane.getText();
    }

    public void setPaneText(String string) {
        textPane.setText(string);
    }

    /**
     * @return java.util.List<java.lang.String>
     * @description 输入面板添加文件后可以获取文件路径列表
     * @author Murray Law
     * @date 2020/11/18 15:22
     */
    public List<String> getFilePaths() {
        List<String> filePaths = new ArrayList<>();
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            Element element = this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i);

            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(element.getAttributes());
            if (component instanceof MyFileJPanel) {
                //插入文件的路径
                MyFileJPanel myFileJPanel = (MyFileJPanel) component;
                filePaths.add(myFileJPanel.getFilePath());
            }
        }
        return filePaths;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @description 获取输入框的@列表
     * @author Murray Law
     * @date 2020/11/18 15:21
     */
    public List<String> getAtList() {
        List<String> atList = new ArrayList<>();
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof JLabel) {
                //插入文件的路径
                JLabel label = (JLabel) component;
                atList.add(label.getText());
            }
        }
        return atList;
    }

    public List<String> getMyLabelAttrList() {
        List<String> attrList = new ArrayList<>();
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof MyLabel) {
                //插入文件的路径
                MyLabel label = (MyLabel) component;
                attrList.add(label.getAttribute());
            }
        }
        return attrList;
    }
    public List<String> getMyLabelTextList() {
        List<String> attrList = new ArrayList<>();
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof MyLabel) {
                //插入文件的路径
                MyLabel label = (MyLabel) component;
                attrList.add(label.getText());
            }
        }
        return attrList;
    }
    /**
     * @description 获取textPanel里MailUserLabel组件的所有参数
     * @author Murray Law
     * @date 2020/12/18 15:23
     * @param
     * @return java.lang.String[]
     */
    public String[] getMailUserArray() {
        List<String> attrList = new ArrayList<>();
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof MailUserLabel) {
                //插入文件的路径
                MailUserLabel label = (MailUserLabel) component;
                attrList.add(label.getUserMailNo());
            }
        }
        String[] userNoArray = new String[attrList.size()];
        return attrList.toArray(userNoArray);
    }
    public List<String> getMailUserList() {
        List<String> attrList = new ArrayList<>();
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof MailUserLabel) {
                //插入文件的路径
                MailUserLabel label = (MailUserLabel) component;
                attrList.add(label.getUserMailNo());
            }
        }
        return attrList;
    }

    public int getMyLabelAttrSum() {
        int count = 0;
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof MyLabel) {
                count += 1;
            }
        }
        return count;
    }

    public int getMyFileJPanelAttrSum() {
        int count = 0;
        //遍历输入面板的所有元素
        for (int i = 0; i < this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElementCount(); i++) {
            //获取文件对应的myFileJPanel
            Component component = StyleConstants.getComponent(this.textPane.getStyledDocument().getRootElements()[0].getElement(0).getElement(i).getAttributes());
            if (component instanceof MyFileJPanel) {
                count += 1;
            }
        }
        return count;
    }

    public JTextPane getTextPane() {
        return textPane;
    }

}
