package com.murray.view.vo.cell;

import com.murray.utils.CommonUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Calendar;

public class MeetingTableCellRender extends DefaultTableCellRenderer {
    private Color todayColor;

    public MeetingTableCellRender(Color todayColor) {
        this.todayColor = todayColor;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == column) {
            c.setBackground(todayColor);
        }
        JTextArea textArea = new JTextArea();
        textArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        textArea.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        if (value != null) {
            int countStr = CommonUtil.countStr(value.toString(), "\n");
            table.setRowHeight(row,(countStr+1)*18);
            textArea.setText(value.toString());
            c = textArea;
            c.setBackground(Color.yellow);
        }
        return c;
    }

}