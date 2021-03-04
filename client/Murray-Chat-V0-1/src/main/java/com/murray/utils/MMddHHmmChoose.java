package com.murray.utils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Murray Law
 * @describe 时间选择工具
 * @createTime 2020/11/22
 */
public class MMddHHmmChoose extends JPanel {
    private JLabel yyyyLabel;
    private JTextField MMTextField, ddTextField, HHTextField, mmTextField;


    public MMddHHmmChoose(Calendar calendar) {
        int btnY = 9;
        int intYyyy = calendar.get(Calendar.YEAR);
        int intMM = calendar.get(Calendar.MONTH) + 1;
        int intDd = calendar.get(Calendar.DAY_OF_MONTH);
        int intHH = calendar.get(Calendar.HOUR_OF_DAY);
        int intMm = calendar.get(Calendar.MINUTE);

        String yyyy = String.valueOf(intYyyy);
        String MM = String.valueOf(intMM);
        String dd = String.valueOf(intDd);
        String HH = String.valueOf(intHH);


        setLayout(null);
        //当前年份(不可编辑)
        yyyyLabel = new JLabel(yyyy + "年");
        yyyyLabel.setBounds(0, 0, 50, 30);
        add(yyyyLabel);

        //月份输入框
        MMTextField = getTextField(MM);
        MMTextField.setBounds(60, 5, 30, 20);
        add(MMTextField);
        //月份减按钮
        JButton MMReduceBtn = getReduceBtn(47, btnY);
        add(MMReduceBtn);
        //月份加按钮
        JButton MMAddBtn = getAddBtn(90, btnY);
        add(MMAddBtn);
        //月标签
        JLabel monthLabel = new JLabel("月");
        monthLabel.setBounds(110, 0, 50, 30);
        add(monthLabel);

        //当月的天输入框
        ddTextField = getTextField(dd);
        ddTextField.setBounds(145, 5, 30, 20);
        add(ddTextField);
        //日子减按钮
        JButton dayReduceBtn = getReduceBtn(132, btnY);
        add(dayReduceBtn);
        //日子加按钮
        JButton dayAddButton = getAddBtn(175, btnY);
        add(dayAddButton);
        //日标签
        JLabel dayLabel = new JLabel("日");
        dayLabel.setBounds(195, 0, 50, 30);
        add(dayLabel);

        //当日小时输入框
        HHTextField = getTextField(HH);
        HHTextField.setBounds(250, 5, 30, 20);
        add(HHTextField);
        //":"标签
        JLabel timeLabel = new JLabel(":");
        timeLabel.setBounds(283, 0, 20, 30);
        add(timeLabel);
        //分钟输入框
        String mm;
        if (intMm < 30) {
            mm = "30";
        } else {
            HHTextField.setText(String.valueOf((intHH + 1)));
            mm = "00";
        }
        mmTextField = getTextField(mm);
        mmTextField.setBounds(290, 5, 30, 20);
        add(mmTextField);
        //添加月份加按钮监听
        MMAddBtn.addActionListener(e -> {
            syncYearWhenMonthAdd(yyyyLabel, MMTextField);
        });
        //添加月份减按钮监听
        MMReduceBtn.addActionListener(e -> {
            syncYearWhenMonthReduce(yyyyLabel, MMTextField);
        });
        //添加日加按钮监听
        dayAddButton.addActionListener(e -> {
            syncYearMonthAndDayWhenDayAdd(yyyyLabel, MMTextField, ddTextField);
        });
        //添加日减按钮监听
        dayReduceBtn.addActionListener(e -> {
            syncYearMonthAndDayReduce(yyyyLabel, MMTextField, ddTextField);
        });
    }

    private void syncYearMonthAndDayReduce(JLabel yyyyLabel, JTextField MMTextField, JTextField ddTextField) {
        //获取年参数
        String yyyyLabelText = yyyyLabel.getText();
        yyyyLabelText = yyyyLabelText.substring(0, 4);
        int intFieldYyyy = Integer.parseInt(yyyyLabelText);
        //获取月参数
        String strFieldMM = MMTextField.getText();
        int intFieldMM = Integer.parseInt(strFieldMM);
        //获取日参数
        String strFieldDd = ddTextField.getText();
        int intFieldDay = Integer.parseInt(strFieldDd);
        //同步年月日
        if (intFieldDay > 1) {
            strFieldDd = String.valueOf(intFieldDay - 1);
            ddTextField.setText(strFieldDd);
        } else {
            intFieldMM -= 1;
            try {
                ddTextField.setText(String.valueOf(getStringDayOfMonth(intFieldYyyy + "-" + intFieldMM)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            syncYearWhenMonthReduce(yyyyLabel, MMTextField);
        }
    }

    /**
     * @param yyyyLabel
     * @param MMTextField
     * @param ddTextField
     * @return void
     * @description 当天数按钮添加, 同步年月日
     * @author Murray Law
     * @date 2020/11/23 9:05
     */
    private void syncYearMonthAndDayWhenDayAdd(JLabel yyyyLabel, JTextField MMTextField, JTextField ddTextField) {
        //获取年参数
        String yyyyLabelText = yyyyLabel.getText();
        yyyyLabelText = yyyyLabelText.substring(0, 4);
        int intFieldYyyy = Integer.parseInt(yyyyLabelText);
        //获取月参数
        String strFieldMM = MMTextField.getText();
        int intFieldMM = Integer.parseInt(strFieldMM);
        //获取日参数
        String strFieldDd = ddTextField.getText();
        int intFieldDay = Integer.parseInt(strFieldDd);
        //同步年月日
        try {
            if (intFieldDay >= getStringDayOfMonth(intFieldYyyy + "-" + intFieldMM + "-" + intFieldDay)) {
                ddTextField.setText("1");
                syncYearWhenMonthAdd(yyyyLabel, MMTextField);
            } else {
                strFieldDd = String.valueOf(intFieldDay + 1);
                ddTextField.setText(strFieldDd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param yyyyLabel
     * @param MMTextField
     * @return void
     * @description 当月份减, 同步年
     * @author Murray Law
     * @date 2020/11/23 8:49
     */
    private void syncYearWhenMonthReduce(JLabel yyyyLabel, JTextField MMTextField) {
        //获取输入框的月
        String strFieldMM = MMTextField.getText();
        int intFieldMM = Integer.parseInt(strFieldMM);
        //获取标签的年
        String yyyyLabelText = yyyyLabel.getText();
        yyyyLabelText = yyyyLabelText.substring(0, 4);
        int intFieldYyyy = Integer.parseInt(yyyyLabelText);
        //同步操作
        if (intFieldMM > 1) {
            strFieldMM = String.valueOf(intFieldMM - 1);
            MMTextField.setText(strFieldMM);
        } else {
            yyyyLabelText = (intFieldYyyy - 1) + "年";
            MMTextField.setText("12");
            yyyyLabel.setText(yyyyLabelText);
        }
    }

    /**
     * @param yyyyLabel
     * @param MMTextField
     * @return void
     * @description 同步年月
     * @author Murray Law
     * @date 2020/11/23 8:46
     */
    private void syncYearWhenMonthAdd(JLabel yyyyLabel, JTextField MMTextField) {
        //获取数字月份
        String strFieldMM = MMTextField.getText();
        int intFieldMM = Integer.parseInt(strFieldMM);
        //获取数字年份
        String yyyyLabelText = yyyyLabel.getText();
        yyyyLabelText = yyyyLabelText.substring(0, 4);
        int intFieldYyyy = Integer.parseInt(yyyyLabelText);
        //同步数据
        if (intFieldMM > 11) {
            yyyyLabelText = (intFieldYyyy + 1) + "年";
            MMTextField.setText("1");
            yyyyLabel.setText(yyyyLabelText);
        } else {
            strFieldMM = String.valueOf(intFieldMM + 1);
            MMTextField.setText(strFieldMM);
        }
    }

    /**
     * @param btnX 按钮X轴
     * @param btnY 按钮Y轴
     * @return javax.swing.JButton
     * @description 获取"-"按钮
     * @author Murray Law
     * @date 2020/11/23 1:33
     */
    private JButton getReduceBtn(int btnX, int btnY) {
        ImageIcon icon = PictureManipulationUtil.getImageIcon("icon/reducebtn.png", 12, 12);
        JButton btn = new JButton(icon);
        btn.setBounds(btnX, btnY, 12, 12);
        return btn;
    }

    /**
     * @param btnX 按钮X轴
     * @param btnY 按钮Y轴
     * @return javax.swing.JButton
     * @description 获取＋按钮
     * @author Murray Law
     * @date 2020/11/23 1:33
     */
    private JButton getAddBtn(int btnX, int btnY) {
        ImageIcon imageIcon = PictureManipulationUtil.getImageIcon("icon/addbtn.jpg", 12, 12);
        JButton btn = new JButton(imageIcon);
        btn.setBounds(btnX, btnY, 12, 12);
        return btn;
    }

    /**
     * @param dd 日
     * @return javax.swing.JTextField
     * @description 获取输入文本框
     * @author Murray Law
     * @date 2020/11/23 1:33
     */
    private JTextField getTextField(String dd) {
        JTextField jTextField = new JTextField(dd);
        jTextField.setHorizontalAlignment(JTextField.CENTER);
        //限制操作
        jTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 && jTextField.getText().length() < 3)) {
                    e.consume(); //关键，屏蔽掉非法输入
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String text = jTextField.getText();
                if (text.length() > 2) {
                    text = text.substring(0, 2);
                    jTextField.setText(text);
                }
            }

        });
        return jTextField;
    }

    /**
     * @param dateFormatStr 要格式化的字符串
     * @return void
     * @description 入参格式为"yyyy-MM-dd"
     * @author Murray Law
     * @date 2020/11/23 8:28
     */
    public int getStringDayOfMonth(String dateFormatStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(dateFormatStr));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public Date getDateFormat() throws ParseException, NumberFormatException {
        //获取年参数
        String yyyyLabelText = yyyyLabel.getText();
        yyyyLabelText = yyyyLabelText.substring(0, 4);
        //获取月参数
        String strFieldMM = MMTextField.getText();
        //获取日参数
        String strFieldDd = ddTextField.getText();
        //获取小时参数
        String strFieldHH = HHTextField.getText();
        //获取分钟参数
        String strFieldMm = mmTextField.getText();
        //获取序列化字符串
        String dateFormatStr = yyyyLabelText + "-" + strFieldMM + "-" + strFieldDd + "\t" + strFieldHH + ":" + strFieldMm;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\tHH:mm");
        return sdf.parse(dateFormatStr);
    }

    public Integer getYear() {
        String yyyyLabelText = yyyyLabel.getText();
        yyyyLabelText= yyyyLabelText.substring(0, 4);
        return Integer.parseInt(yyyyLabelText);
    }

    public Integer getMonth() {
        String MMFieldText = MMTextField.getText();
        return Integer.parseInt(MMFieldText);
    }

    public Integer getDay() {
        String ddFieldText = ddTextField.getText();
        return Integer.parseInt(ddFieldText);
    }

    public Integer getHour() {
        String HHFieldText = HHTextField.getText();
        return Integer.parseInt(HHFieldText);
    }

    public Integer getMinute() {
        String mmFieldText = mmTextField.getText();
        return Integer.parseInt(mmFieldText);
    }
}
