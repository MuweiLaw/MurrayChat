package com.murray.view.vo.cell;

import com.murray.dto.response.DepartmentResponse;

import javax.swing.*;
import java.awt.*;

/**
 * @author Murray Law
 * @describe 通讯录单元
 * @createTime 2020/12/7
 */
public class AddressBookCell extends JLabel {

    public AddressBookCell(DepartmentResponse response) {
        super();
        setLayout(null);
        setOpaque(true);
        setBackground(Color.white);
        setText("      " + response.getName() + "  (" + response.getSum() + ")");
    }

}
