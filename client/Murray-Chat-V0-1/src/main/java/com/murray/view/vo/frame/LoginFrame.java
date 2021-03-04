package com.murray.view.vo.frame;

import com.alibaba.fastjson.JSON;
import com.murray.ClientMainApplication;
import com.murray.entity.LastLoginInfo;
import com.murray.handler.LoginHandler;
import com.murray.utils.ColorUtil;
import com.murray.utils.FontUtil;
import com.murray.utils.OperationalBtnUtil;
import com.murray.utils.PictureManipulationUtil;
import com.murray.view.vo.cell.CircleButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.murray.utils.PictureManipulationUtil.getImageIcon;

/**
 * @author Murray Law
 * @describe 登录后界面窗口。
 */
public class LoginFrame extends JFrame {
    private JLabel titleLabel, labelByUserName, loginLabel, userIdUnderline,
            passwordUnderline, labelByLockIcon, labelByUserIDIcon, promptLabel= new JLabel();
    private JTextField userNameFiled;
    private JPasswordField passwordField;
    private JButton registerAnAccount, qrCodeButton;
    private JPanel contentPane;
    private ImageIcon lockIcon, userIdIcon, loginAvatarIcon, qrCodeIcon;
    private JCheckBox autoLogin, rememberPassword;
    private Color lightBlue = new Color(18, 185, 245);
    private MouseAdapter userNameMouseAdapter;
    private MouseAdapter passWordMouseAdapter;
    private int frameWid = 375, frameHei = 500;

    public LoginFrame() {
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

        ImageIcon loginBlueIcon = getImageIcon("v1icon/loginBlue.png", 300, 45);
        ImageIcon loginGreyIcon = getImageIcon("v1icon/loginGrey.png", 300, 45);
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
        //headPanel.add(logoLabel);

        add(headPanel);


        //设置登录头像缩略大小，并面板布局中
        loginAvatarIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("images/fefaultAvatar.jpg")));
        loginAvatarIcon.setImage(loginAvatarIcon.getImage().getScaledInstance(80, 80, Image.SCALE_AREA_AVERAGING));


        //账户输入框
        userNameFiled = new JTextField();
        userNameFiled.setBounds(80, 273, 250, 40);
        userNameFiled.setOpaque(false);
        userNameFiled.setBorder(null);
        userNameFiled.setFont(FontUtil.MICROSOFT_YA_HEI_18);


        //账号输入提示
        JLabel namePrompt = new JLabel("请输入手机号码/邮箱登录");
        namePrompt.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        namePrompt.setBounds(80, 277, 250, 30);
        namePrompt.setForeground(Color.lightGray);

        //设置账户名图标缩略大小，
        labelByUserIDIcon = new JLabel();
        userIdIcon = getImageIcon("v1icon/user.png", 25, 25);
        labelByUserIDIcon.setIcon(userIdIcon);
        labelByUserIDIcon.setBounds(40, 280, 300, 25);
        //账户名输入框下划线
        userIdUnderline = new JLabel("__________________________________________", JLabel.LEFT);
        userIdUnderline.setForeground(Color.lightGray);
        userIdUnderline.setBounds(40, 295, 300, 25);
        userNameMouseAdapter = getMouseAdapter(userIdUnderline);
        //账户内容加入面板
        contentPane.add(userNameFiled);
        contentPane.add(namePrompt);
        contentPane.add(labelByUserIDIcon);
        contentPane.add(userIdUnderline);


//        labelByPassword = new JLabel("密码:", JLabel.CENTER);
//        labelByPassword.setBounds(-50, 200, 250, 20);
//        contentPane.add(labelByPassword);
        //密码输入框
        passwordField = new JPasswordField();
        passwordField.setBounds(80, frameHei - 183, 200, 40);
        passwordField.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);

        //账号输入提示
        JLabel pwdPrompt = new JLabel("请输入密码");
        pwdPrompt.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        pwdPrompt.setBounds(80, frameHei - 183, 250, 40);
        pwdPrompt.setForeground(Color.lightGray);
        contentPane.add(pwdPrompt);
        //设置密码锁图标缩略大小，
        labelByLockIcon = new JLabel();
        lockIcon = getImageIcon("v1icon/password.png", 25, 25);
        labelByLockIcon.setIcon(lockIcon);
        labelByLockIcon.setBounds(40, frameHei - 175, 300, 25);
        //密码输入框下划线
        passwordUnderline = new JLabel("__________________________________________", JLabel.LEFT);
        passwordUnderline.setBounds(40, frameHei - 160, 300, 20);
        passwordUnderline.setForeground(Color.lightGray);

        //密码输入框可见按钮
        ImageIcon visibleIcon = PictureManipulationUtil.getImageIcon("v1icon/visible.png", 15, 10);
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
        promptLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        promptLabel.setForeground(ColorUtil.WARNING_COLOR);
        promptLabel.setBounds((frameWid-300)/2,365,300,20);
        contentPane.add(promptLabel);
        //图片放入头像按钮
        LastLoginInfo lastLoginInfo = readLastLoginInfo();
        if (null == lastLoginInfo) {
            CircleButton headSculptureButton = new CircleButton("");
            headSculptureButton.setBounds((frameWid - 120) / 2, 130, 120, 120);
            headSculptureButton.setBackground(new Color(230, 230, 230));
            //头像按钮加入面板
            contentPane.add(headSculptureButton);
        } else {
            ImageIcon avatarFrameIcon = getImageIcon("v1icon/avatarFrame.png", 120, 120);
            JLabel avatarFrameLabel = new JLabel(avatarFrameIcon);
            avatarFrameLabel.setBounds((frameWid - 120) / 2, 130, 120, 120);
            ImageIcon avatarIcon = new ImageIcon(System.getProperty("user.dir") + "\\avatar\\" + lastLoginInfo.getUserAvatar());
            if (!new File(System.getProperty("user.dir") + "\\avatar\\" + lastLoginInfo.getUserAvatar()).exists()){
                avatarIcon=new ImageIcon(System.getProperty("user.dir") + "\\avatar\\defaultUserAvatar.jpg");
            }
            avatarIcon.setImage(avatarIcon.getImage().getScaledInstance(120, 120, Image.SCALE_AREA_AVERAGING));

            JLabel avatarLabel = new JLabel(avatarIcon);
            avatarLabel.setBounds((frameWid - 120) / 2, 130, 120, 120);
            contentPane.add(avatarFrameLabel);
            contentPane.add(avatarLabel);
            userNameFiled.setText(lastLoginInfo.getUserNo());
            passwordField.setText(lastLoginInfo.getUserPassWord());
            contentPane.remove(pwdPrompt);
            contentPane.remove(namePrompt);
        }
        //添加密码输入控件
        contentPane.add(passwordField);
        contentPane.add(labelByLockIcon);
        contentPane.add(passwordUnderline);

        // 创建复选框
        autoLogin = new JCheckBox("自动登录");
        rememberPassword = new JCheckBox("记住密码");
        autoLogin.setBounds(120, 315, 90, 25);
        autoLogin.setForeground(Color.gray);
        autoLogin.setBorder(null);
        autoLogin.setContentAreaFilled(false);
        rememberPassword.setBounds(240, 315, 90, 25);
        rememberPassword.setForeground(Color.gray);
        rememberPassword.setBorder(null);
        rememberPassword.setContentAreaFilled(false);

        //注册账号按钮
        registerAnAccount = new JButton("找回密码");
        registerAnAccount.setForeground(Color.gray);
        registerAnAccount.setBounds(350, 310, 70, 30);
        registerAnAccount.setBorder(null);
        registerAnAccount.setContentAreaFilled(false);

        //登录按钮
        //loginButton = new JButton("登录");

        loginLabel = new JLabel();
        if (passwordField.getText().equals("123456") || passwordField.getPassword().length > 7) {
            loginLabel.setIcon(loginBlueIcon);
        } else {
            loginLabel.setIcon(loginGreyIcon);
        }
        if (passwordField.getPassword().length > 0) {
            contentPane.add(showLabel);
            contentPane.add(clearLabel);
        }
        loginLabel.setBounds(40, frameHei - 80, 300, 45);
        contentPane.add(loginLabel);
        //注册账号按钮
        registerAnAccount = new JButton("注册账号");
        registerAnAccount.setForeground(Color.gray);
        registerAnAccount.setBounds(10, 395, 70, 30);
        registerAnAccount.setBorder(null);
        registerAnAccount.setContentAreaFilled(false);
        //contentPane.add(registerAnAccount);

        //二维码按钮
        //addQRCode();

        //等待图标
        ImageIcon loadingIcon=new ImageIcon(ClientMainApplication.class.getClassLoader().getResource("v1icon/loading.png"));
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
                promptLabel.setHorizontalAlignment(JLabel.CENTER);
                promptLabel.setText(null);
                promptLabel.setIcon(loadingIcon);
                //开发环境下连接服务端
                new LoginHandler().doConnectByDEV();
            }
        });
        //退出按钮监听事件
        exitBtn.addActionListener(event -> System.exit(0));
        //
        KeyAdapter keyAdapter = new KeyAdapter() {
            String phoneRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
            String mailRegex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

            public void keyReleased(KeyEvent e) {
                if (lastLoginInfo != null && !lastLoginInfo.getUserNo().equals(userNameFiled.getText()) && passwordField.getPassword().length == 0) {
                    passwordField.setText("123456");
                }
                if (passwordField.getPassword().length > 0) {
                    contentPane.add(showLabel);
                    contentPane.add(clearLabel);
                } else {
                    contentPane.remove(showLabel);
                    contentPane.remove(clearLabel);
                }

                if (!userNameFiled.getText().isEmpty() && (passwordField.getText().equals("123456") || passwordField.getPassword().length > 7)) {
                    Pattern patternPhone = Pattern.compile(phoneRegex);
                    Matcher matcherPhone = patternPhone.matcher(userNameFiled.getText());
                    boolean isMatchByPhone = matcherPhone.matches();
                    Pattern patternMail = Pattern.compile(mailRegex);
                    Matcher matcherMail = patternMail.matcher(userNameFiled.getText());
                    boolean isMatchByMail = matcherMail.matches();
                    if (isMatchByPhone || isMatchByMail) {
                        loginLabel.setIcon(loginBlueIcon);
                        return;
                    }
                }
                loginLabel.setIcon(loginGreyIcon);
                contentPane.updateUI();

            }
        };
        //焦点监听器
        userNameFiled.addFocusListener(getFocusAdapter(userNameFiled, userIdUnderline, userNameMouseAdapter, namePrompt));
        userNameFiled.addKeyListener(keyAdapter);
        passWordMouseAdapter = getMouseAdapter(passwordUnderline);
        passwordField.addMouseListener(passWordMouseAdapter);
        passwordField.addFocusListener(getFocusAdapter(passwordField, passwordUnderline, passWordMouseAdapter, pwdPrompt));
        passwordField.addKeyListener(keyAdapter);
        //设置可见
        setVisible(true);
    }

    /**
     * @param
     * @return void
     * @description 添加二维码按钮 todo 后端实现
     * @author Murray Law
     * @date 2020/10/25 5:11
     */
    private void addQRCode() {
        qrCodeButton = new JButton();
        qrCodeIcon = getImageIcon("icon/qrcode.png", 30, 30);
        qrCodeButton.setBounds(505, 395, 30, 30);
        qrCodeButton.setSize(qrCodeIcon.getImage().getWidth(null), qrCodeIcon.getImage().getHeight(null));
        qrCodeButton.setIcon(qrCodeIcon);
        qrCodeButton.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        qrCodeButton.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
        qrCodeButton.setBorderPainted(false);//不打印边框
        qrCodeButton.setBorder(null);//除去边框
        qrCodeButton.setText(null);//除去按钮的默认名称
        qrCodeButton.setFocusPainted(false);//除去焦点的框
        qrCodeButton.setContentAreaFilled(false);//除去默认的背景填充
        qrCodeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        qrCodeButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        contentPane.add(qrCodeButton);
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
        return userNameFiled;
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
     * @param remove
     * @return java.awt.event.FocusAdapter
     * @description 输入框焦点监听
     * @author Murray Law
     * @date 2020/12/28 6:38
     */
    private FocusAdapter getFocusAdapter(JTextField textField, JLabel underscore, MouseAdapter mouseAdapter, JLabel remove) {
        return new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                textField.removeMouseListener(mouseAdapter);
                underscore.setForeground(lightBlue);
                remove.setText(null);
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

    private LastLoginInfo readLastLoginInfo() {
        String lastLoginInfo = "";
        try {
            File file = new File(System.getProperty("user.dir") + "\\setting\\lastLogin"); //文件路径（路径+文件名）
            FileReader fileReader = new FileReader(file); //用于将数据读取文件
            char[] flush = new char[1024];//字节读入数组
            int len = fileReader.read(flush);
            lastLoginInfo = new String(flush, 0, len);
            fileReader.close(); //关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSON.parseObject(lastLoginInfo, LastLoginInfo.class);
    }


    public JLabel getLoginButton() {
        return loginLabel;
    }
    public JLabel getPromptLabel() {
        return promptLabel;
    }
}