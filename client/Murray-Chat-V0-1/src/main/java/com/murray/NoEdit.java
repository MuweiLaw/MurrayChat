package com.murray;

import com.murray.view.vo.panel.RichTextPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class NoEdit extends JFrame {


    public void main1() {
//        JFrame frame = new JFrame("Non-Delete");
        setLayout(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        RichTextPanel richTextPanel = new RichTextPanel(panel, null,0, 0, 400, 400);
        richTextPanel.insertComponent(new JLabel("@你爸爸草草草草草草草草草草草草草草草草"));
        System.out.println(richTextPanel.getFilePaths().size());
        System.out.println(richTextPanel.getAtList().size());

        setContentPane(panel);
        setSize(500, 500);
        setVisible(true);
        panel.setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        new NoEdit().main1();
    }

    /**
     * 获取剪贴板内容(粘贴)
     */
    public String getClipboardString() {
        //获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //获取剪贴板内容
        Transferable trans = clipboard.getContents(null);
        if (trans != null) {
            //判断剪贴板内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String clipboardStr = null;
                try {
                    //获取剪贴板的文本内容
                    clipboardStr = (String) trans.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return clipboardStr;
            }
        }
        return null;
    }

    /**
     * 设置剪贴板内容(复制)
     */
    public static void setClipboardString(String str) {
        //获取协同剪贴板，单例
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //封装文本内容
        Transferable trans = new StringSelection(str);
        //把文本内容设置到系统剪贴板上
        clipboard.setContents(trans, null);
    }
}