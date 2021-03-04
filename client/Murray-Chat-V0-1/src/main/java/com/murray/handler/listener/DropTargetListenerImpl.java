package com.murray.handler.listener;

import com.murray.view.vo.cell.MyFileJPanel;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

/**
 * 拖拽目标监听器实现
 */
public class DropTargetListenerImpl implements DropTargetListener {

    /**
     * 用于显示拖拽的数据
     */
    private JTextPane textPane;
    private JButton enabledBtn = null;

    public DropTargetListenerImpl(JTextPane textPane, JButton enabledBtn) {
        this.textPane = textPane;
        this.enabledBtn = enabledBtn;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {

//        System.out.println("dragEnter: 拖拽目标进入组件区域");
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
//        System.out.println("dragOver: 拖拽目标在组件区域内移动");
    }

    @Override
    public void dragExit(DropTargetEvent dte) {

//        System.out.println("dragExit: 拖拽目标离开组件区域");
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

//        System.out.println("dropActionChanged: 当前 drop 操作被修改");
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        // 一般情况下只需要关心此方法的回调
//        System.out.println("drop: 拖拽目标在组件区域内释放");

        boolean isAccept = false;

        try {
            /*
             * 1. 文件: 判断拖拽目标是否支持文件列表数据（即拖拽的是否是文件或文件夹, 支持同时拖拽多个）
             */
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                // 接收拖拽目标数据
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                isAccept = true;

                // 以文件集合的形式获取数据
                List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                // 把文件路径输出到文本区域
                if (files != null && files.size() > 0) {
                    for (File file : files) {
                        insertComponent(file);
                    }
                }
            }

            /*
             * 2. 文本: 判断拖拽目标是否支持文本数据（即拖拽的是否是文本内容, 或者是否支持以文本的形式获取）
             */
            if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                // 接收拖拽目标数据
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                isAccept = true;
                // 以文本的形式获取数据
                String text = textPane.getText() + dtde.getTransferable().getTransferData(DataFlavor.stringFlavor).toString();
                // 输出到文本区域
                textPane.setText(text);
            }

            /*
             * 3. 图片: 判断拖拽目标是否支持图片数据。注意: 拖拽图片不是指以文件的形式拖拽图片文件,
             *          而是指拖拽一个正在屏幕上显示的并且支持拖拽的图片（例如网页上显示的图片）。
             */
//            if (dtde.isDataFlavorSupported(DataFlavor.imageFlavor)) {
//                // 接收拖拽目标数据
//                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
//                isAccept = true;
//
//                // 以图片的形式获取数据
//                Image image = (Image) dtde.getTransferable().getTransferData(DataFlavor.imageFlavor);
//
//                // 获取到 image 对象后, 可以对该图片进行相应的操作（例如: 用组件显示、图形变换、保存到本地等）,
//                // 这里只把图片的宽高输出到文本区域
//                textPane.setText("图片: " + image.getWidth(null) + " * " + image.getHeight(null) + "\n");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果此次拖拽的数据是被接受的, 则必须设置拖拽完成（否则可能会看到拖拽目标返回原位置, 造成视觉上以为是不支持拖拽的错误效果）
        if (isAccept) {
            dtde.dropComplete(true);
            //设置按钮可用
            if (enabledBtn!=null){
                enabledBtn.setEnabled(true);
            }
        }
    }

    private void insertComponent(File file) {
        MyFileJPanel myFileJPanel = new MyFileJPanel(file);
        textPane.insertComponent(myFileJPanel); // 插入Component
    }
}