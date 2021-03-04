package com.murray;

import com.murray.cache.ClientCache;
import com.murray.view.vo.frame.LoginFrame;

import javax.swing.*;

/**
 * @author Murray Law
 * @describe 聊天室客户端总启动器
 */
public class ClientMainApplication2 {
    public static void main(String[] args) {

        // 此处处于 主线程，提交任务到 事件调度线程 创建窗口
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        // 此处处于 事件调度线程
                        ClientCache.loginFrame = new LoginFrame();
                    }
                }
        );
    }
}