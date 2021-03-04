package com.murray.view.vo.frame;

import com.murray.dto.request.ChangePwdRequest;
import com.murray.handler.ClientPacketCodeCompile;
import com.murray.utils.FontUtil;
import com.murray.utils.OperationalBtnUtil;
import com.murray.utils.PictureManipulationUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import static com.murray.cache.ClientCache.CHANNEL;
import static com.murray.utils.PictureManipulationUtil.getImageIcon;

/**
 * @author Murray Law
 * @describe 修改密码窗口。
 */
public class ChangePasswordFrame extends JFrame {
    private JLabel  loginLabel, userIdUnderline,
            passwordUnderline, labelByLockIcon, labelByUserIDIcon, promptLabel= new JLabel();
    private JTextField oldPwdFiled;
    private JPasswordField passwordField;
    private JButton registerAnAccount, qrCodeButton;
    private JPanel contentPane;
    private Color lightBlue = new Color(18, 185, 245);
    private MouseAdapter userNameMouseAdapter;
    private MouseAdapter passWordMouseAdapter;
    private int frameWid = 375, frameHei = 500;

    public ChangePasswordFrame(String userNo,String pwd) {
        //隐藏windows的窗格边框
        this.setUndecorated(true);
        this.setIconImage(PictureManipulationUtil.getImageIcon("icon/expression.jpg", 100, 100).getImage());
        setTitle("Murray在线聊天工具");
        setSize(frameWid, frameHei);
        setResizable(false);//不可调整
        setLocationRelativeTo(null);//居中
        //setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭即退出
        setBackground(Color.white);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        ImageIcon loginBlueIcon = getImageIcon("v1icon/changePwdBlue.png", 300, 45);
        ImageIcon loginGreyIcon = getImageIcon("v1icon/changePwdGrey.png", 300, 45);
        //getLayeredPane().add(lbBg, new Integer(Integer.MIN_VALUE));
        //获取frame的顶层容器,并设置为透明
        JPanel top = (JPanel) getContentPane();
        //
        top.setOpaque(false);
        JPanel headPanel = new JPanel(null);
        JButton exitBtn = getExitBtn();
        headPanel.setBounds(-1, -1, frameWid + 2, 50);
        headPanel.setBackground(Color.white);
        headPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        headPanel.add(exitBtn);
        OperationalBtnUtil.getZoomOutButton(this, headPanel, frameWid - 55, 10, 15, 15);
        //logo
        JLabel logoLabel = new JLabel(getImageIcon("v1icon/loginLOGO.png", 150, 25));
        logoLabel.setBounds(5, 10, 150, 25);
        headPanel.add(logoLabel);

        add(headPanel);




        //账户输入框
        oldPwdFiled = new JTextField();
        oldPwdFiled.setText(pwd);
        oldPwdFiled.setBounds(100, 173, 250, 40);
        oldPwdFiled.setOpaque(false);
        oldPwdFiled.setBorder(null);
        oldPwdFiled.setEditable(false);
        oldPwdFiled.setFont(FontUtil.MICROSOFT_YA_HEI_18);


        //账号输入提示
        JLabel oldPwdLabel = new JLabel("旧密码");
        oldPwdLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        oldPwdLabel.setBounds(40, 177, 250, 30);
        oldPwdLabel.setForeground(Color.lightGray);

        //账户名输入框下划线
        userIdUnderline = new JLabel("__________________________________________", JLabel.LEFT);
        userIdUnderline.setForeground(Color.lightGray);
        userIdUnderline.setBounds(40, 195, 300, 25);
        userNameMouseAdapter = getMouseAdapter(userIdUnderline);
        //账户内容加入面板
        contentPane.add(oldPwdFiled);
        contentPane.add(oldPwdLabel);
        contentPane.add(userIdUnderline);


//        labelByPassword = new JLabel("密码:", JLabel.CENTER);
//        labelByPassword.setBounds(-50, 200, 250, 20);
//        contentPane.add(labelByPassword);
        //密码输入框
        passwordField = new JPasswordField();
        passwordField.setBounds(100, frameHei - 283, 200, 40);
        passwordField.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);

        //账号输入提示
        JLabel pwdPrompt = new JLabel("新密码");
        pwdPrompt.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        pwdPrompt.setBounds(40, frameHei - 283, 250, 40);
        pwdPrompt.setForeground(Color.lightGray);
        contentPane.add(pwdPrompt);
        //密码输入框下划线
        passwordUnderline = new JLabel("__________________________________________", JLabel.LEFT);
        passwordUnderline.setBounds(40, frameHei - 260, 300, 20);
        passwordUnderline.setForeground(Color.lightGray);

        //密码输入框可见按钮
        ImageIcon visibleIcon = PictureManipulationUtil.getImageIcon("v1icon/visible.png", 15, 15);
        ImageIcon invisibleIcon = PictureManipulationUtil.getImageIcon("v1icon/Invisible.png", 15, 15);
        JLabel showLabel = new JLabel(invisibleIcon);
        showLabel.setBounds(290, frameHei - 175, 20, 20);
        showLabel.addMouseListener(new MouseAdapter() {
            boolean isShow = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isShow) {
                    isShow = false;
                    showLabel.setIcon(invisibleIcon);
                    passwordField.setEchoChar('*');
                } else {
                    isShow = true;
                    showLabel.setIcon(visibleIcon);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
        //密码输入框移除按钮
        JLabel clearLabel = new JLabel(PictureManipulationUtil.getImageIcon("v1icon/loginClear.png", 15, 15));
        clearLabel.setBounds(315, frameHei - 175, 20, 20);
        clearLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginLabel.setIcon(loginGreyIcon);
                passwordField.setText(null);
                contentPane.remove(clearLabel);
                contentPane.remove(showLabel);
                contentPane.updateUI();
            }
        });

        //登录状态
        promptLabel.setFont(FontUtil.MICROSOFT_YA_HEI_14);
        promptLabel.setForeground(Color.lightGray);
        promptLabel.setBounds((frameWid-300)/2,265,300,20);
        promptLabel.setText("新的密码需不小于8位，可以是数字/字母/字符");
        contentPane.add(promptLabel);
        //添加密码输入控件
        contentPane.add(passwordField);
        contentPane.add(passwordUnderline);

        //登录按钮
        //loginButton = new JButton("登录");

        loginLabel = new JLabel();
        if ( passwordField.getPassword().length > 7) {
            loginLabel.setIcon(loginBlueIcon);
        } else {
            loginLabel.setIcon(loginGreyIcon);
        }
        if (passwordField.getPassword().length > 0) {
            contentPane.add(showLabel);
            contentPane.add(clearLabel);
        }
        loginLabel.setBounds(40, frameHei - 180, 300, 45);
        contentPane.add(loginLabel);
        //注册账号按钮
        registerAnAccount = new JButton("注册账号");
        registerAnAccount.setForeground(Color.gray);
        registerAnAccount.setBounds(10, 395, 70, 30);
        registerAnAccount.setBorder(null);
        registerAnAccount.setContentAreaFilled(false);
        // 进入按钮监听事件
        loginLabel.addMouseListener(new MouseAdapter() {
            Date startTime = null;

            @Override
            public void mouseClicked(MouseEvent e) {
                Date endTime = new Date();
                if (startTime!=null&&endTime.getTime() - startTime.getTime() < 5000) {
                    return;
                } else {
                    startTime = endTime;
                }
                //开发环境下连接服务端
                CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new ChangePwdRequest(userNo,pwd,passwordField.getText())));
            }
        });

        //退出按钮监听事件
        exitBtn.addActionListener(event -> System.exit(0));
        KeyAdapter keyAdapter = new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                if (passwordField.getPassword().length > 0) {
                    contentPane.add(showLabel);
                    contentPane.add(clearLabel);
                } else {
                    contentPane.remove(showLabel);
                    contentPane.remove(clearLabel);
                }

                if (!oldPwdFiled.getText().equals(passwordField.getText()) &&  passwordField.getPassword().length > 7) {
                        loginLabel.setIcon(loginBlueIcon);
                        return;
                }
                loginLabel.setIcon(loginGreyIcon);
                contentPane.updateUI();

            }
        };

        //焦点监听器
        oldPwdFiled.addFocusListener(getFocusAdapter(oldPwdFiled, userIdUnderline, userNameMouseAdapter));
        passWordMouseAdapter = getMouseAdapter(passwordUnderline);
        passwordField.addMouseListener(passWordMouseAdapter);
        passwordField.addFocusListener(getFocusAdapter(passwordField, passwordUnderline, passWordMouseAdapter));
        passwordField.addKeyListener(keyAdapter);
        //设置可见
        setVisible(true);
    }

    /**
     * @param
     * @return void
     * @description 添加退出按钮
     * @author Murray Law
     * @date 2020/10/25 5:10
     */
    private JButton getExitBtn() {
        ImageIcon closeIcon = PictureManipulationUtil.getImageIcon("icon/close.png", 15, 15);
        //退出按钮
        JButton exitButton = new JButton(closeIcon);
        exitButton.setBounds(frameWid - 25, 10, 15, 15);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        return exitButton;
    }



    /**
     * @param
     * @return javax.swing.JTextField
     * @description 获取用户名文本框
     * @author Murray Law
     * @date 2020/10/22 21:35
     */
    public JTextField getUserNameJTextField() {
        return oldPwdFiled;
    }

    /**
     * @param
     * @return javax.swing.JTextField
     * @description 获取用户密码框
     * @author Murray Law
     * @date 2020/10/22 21:35
     */
    public JTextField getUserPasswordField() {
        return passwordField;
    }

    /**
     * @param underscore
     * @return java.awt.event.MouseAdapter
     * @description 下划线鼠标监听
     * @author Murray Law
     * @date 2020/12/28 6:37
     */
    private MouseAdapter getMouseAdapter(JLabel underscore) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                underscore.setForeground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                underscore.setForeground(Color.lightGray);
            }
        };
    }

    /**
     * @param textField
     * @param underscore
     * @param mouseAdapter
     * @return java.awt.event.FocusAdapter
     * @description 输入框焦点监听
     * @author Murray Law
     * @date 2020/12/28 6:38
     */
    private FocusAdapter getFocusAdapter(JTextField textField, JLabel underscore, MouseAdapter mouseAdapter) {
        return new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                textField.removeMouseListener(mouseAdapter);
                underscore.setForeground(lightBlue);
//                if (textField == passwordField) {
//                    loginButton.setEnabled(true);
//                }
            }

            public void focusLost(FocusEvent e) {
                textField.addMouseListener(mouseAdapter);
                underscore.setForeground(Color.lightGray);
//                if (textField == passwordField && getUserPasswordField().getText().isEmpty()) {
//                    loginButton.setEnabled(false);
//                }
            }

        };
    }



    public JLabel getLoginButton() {
        return loginLabel;
    }
    public JLabel getPromptLabel() {
        return promptLabel;
    }
}