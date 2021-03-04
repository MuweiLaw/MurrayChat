package com.murray.view.vo.panel;

import com.murray.dto.request.DepartmentMemberRequest;
import com.murray.dto.request.GroupBasicRequest;
import com.murray.dto.response.AddressBookResponse;
import com.murray.dto.response.DepartmentResponse;
import com.murray.utils.ColorUtil;
import com.murray.utils.DimensionUtil;
import com.murray.utils.FontUtil;
import com.murray.utils.GridBagUtil;
import com.murray.view.vo.cell.AddressBookCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.BorderUtil.BORDER_WHITE_235;

/**
 * @author Murray Law
 * @describe 通讯录面板
 * @createTime 2020/12/7
 */
public class AddressBookBasicScrollPanel extends JScrollPane {
   public static  Map<String, DepartmentResponse> departmentMap;
    private JPanel addressBookPanel = new JPanel(new GridBagLayout());
    private final String myFriendStr = "我的好友", myGroupStr = "我的群组";
    private Map<String, AddressBookCell> cellMap = new HashMap<>();
    private String selected = null;

    public AddressBookBasicScrollPanel() {
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }

    public JPanel initAddressBookPanel(AddressBookResponse response) {

        departmentMap= response.getDepartmentMap();
        addressBookPanel.removeAll();
        //填充标签的大小
        Dimension fillUpDimension = new Dimension(324, 15);
        //我的好友
        AddressBookCell myFriendsLabel = new AddressBookCell(new DepartmentResponse(null, myFriendStr, response.getFriendSum()));
        myFriendsLabel.setPreferredSize(DimensionUtil.DIM_325_70);
        JLabel myFriendGreaterThanLabel = new JLabel(">");
        myFriendGreaterThanLabel.setBounds(DimensionUtil.DIM_325_70.width - 50, (DimensionUtil.DIM_325_70.height - 20) / 2, 20, 20);
        myFriendsLabel.add(myFriendGreaterThanLabel);
        myFriendsLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        myFriendGreaterThanLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        addMouseListener(myFriendsLabel, myFriendStr,myFriendStr);
        addressBookPanel.add(myFriendsLabel, GridBagUtil.wrap());
        //填充
        JLabel fillUpLabel1 = new JLabel();
        fillUpLabel1.setOpaque(true);
        fillUpLabel1.setBackground(Color.white);
        fillUpLabel1.setPreferredSize(fillUpDimension);
        fillUpLabel1.setBorder(BORDER_WHITE_235);
        addressBookPanel.add(fillUpLabel1, GridBagUtil.wrap());
        cellMap.put(myFriendStr, myFriendsLabel);

        //我的群组
        AddressBookCell myGroupsLabel = new AddressBookCell(new DepartmentResponse(null, myGroupStr, response.getGroupSum()));
        myGroupsLabel.setPreferredSize(DimensionUtil.DIM_325_70);
        JLabel myGroupGreaterThanLabel = new JLabel(">");
        myGroupGreaterThanLabel.setBounds(DimensionUtil.DIM_325_70.width - 50, (DimensionUtil.DIM_325_70.height - 20) / 2, 20, 20);
        myGroupsLabel.add(myGroupGreaterThanLabel);
        myGroupsLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        myGroupGreaterThanLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        addMouseListener(myGroupsLabel, myGroupStr,myFriendStr);
        addressBookPanel.add(myGroupsLabel, GridBagUtil.wrap());
        cellMap.put(myGroupStr, myGroupsLabel);
        JLabel fillUpLabel2 = new JLabel();
        fillUpLabel2.setOpaque(true);
        fillUpLabel2.setBackground(Color.white);
        fillUpLabel2.setPreferredSize(fillUpDimension);
        fillUpLabel2.setBorder(BORDER_WHITE_235);
        addressBookPanel.add(fillUpLabel2, GridBagUtil.wrap());
        //部门列表
        departmentMap.forEach((departmentName, departmentResponse) -> {
            AddressBookCell addressBookCell = new AddressBookCell(departmentResponse);
            addressBookCell.setPreferredSize(DimensionUtil.DIM_325_70);
            addMouseListener(addressBookCell, departmentResponse.getDepartmentNo(),departmentName);
            JLabel greaterThanLabel = new JLabel(">");
            greaterThanLabel.setBounds(DimensionUtil.DIM_325_70.width - 50, (DimensionUtil.DIM_325_70.height - 20) / 2, 20, 20);
            addressBookCell.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            greaterThanLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            addressBookCell.add(greaterThanLabel);
            addressBookPanel.add(addressBookCell, GridBagUtil.wrap());
            cellMap.put(departmentResponse.getDepartmentNo(), addressBookCell);
        });
        if (selected != null) {
            cellMap.get(selected).setBackground(Color.lightGray);
        }
        JLabel fillUpLabel3 = new JLabel();
        fillUpLabel3.setPreferredSize(new Dimension(322, CHAT_MAIN_FRAME.mainFrame.getHeight() - 70 * departmentMap.size() - 280));
        addressBookPanel.add(fillUpLabel3);
        addressBookPanel.setBorder(null);
        return addressBookPanel;
    }

    private void addMouseListener(AddressBookCell cell, String departmentNo,String departmentName) {
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selected!=null){ cellMap.get(selected).setBackground(Color.white); }
                cell.setBackground(Color.lightGray);
                selected=departmentNo;
                if (departmentNo.equals(myFriendStr)) {
                    CHANNEL.writeAndFlush(encode(new DepartmentMemberRequest(chatUserByLogIn.getChatUserNo(), departmentNo,departmentName)));
                    return;
                }
                if (departmentNo.equals(myGroupStr)) {
                    CHANNEL.writeAndFlush(encode(new GroupBasicRequest(chatUserByLogIn.getChatUserNo(), (byte)3)));
                    return;
                }
                CHANNEL.writeAndFlush(encode(new DepartmentMemberRequest(chatUserByLogIn.getChatUserNo(), departmentNo,departmentName)));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (selected!=null&&selected.equals(departmentNo)) return;
                cell.setBackground(ColorUtil.WHITE235);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (selected!=null&&selected.equals(departmentNo)) return;
                cell.setBackground(Color.white);
            }
        });
    }

    public void setSelected(String str) {
        this.selected = str;
    }
}
