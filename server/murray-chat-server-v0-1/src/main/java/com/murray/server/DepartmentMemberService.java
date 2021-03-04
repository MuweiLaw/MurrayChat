package com.murray.server;

import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.dto.response.DepartmentMemberResponse;
import com.murray.entity.ChatUser;
import com.murray.entity.ChatUserDepartment;
import com.murray.entity.ChatUserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeMap;

/**
 * @author Murray Law
 * @describe 部门成员服务
 * @createTime 2020/12/8
 */
@Service
public class DepartmentMemberService {
    @Autowired
    private ChatUserService userService;
    @Autowired
    private ChatUserInfoService userInfoService;
    @Autowired
    private ChatDepartmentService departmentService;
    @Autowired
    private ChatUserDepartmentService userDepartmentService;

    @Transactional
    public DepartmentMemberResponse getDepartmentMemberResponse(String requesterNo,String departmentNo,String departmentName) {
        TreeMap<String, ChatUserInfoResponse> userInfoTreeMap = new TreeMap<>();
        List<ChatUserDepartment> chatUserDepartments = userDepartmentService.selectByDepartmentNo(departmentNo);
        chatUserDepartments.forEach(chatUserDepartment -> {
            ChatUserInfoResponse chatUserInfoResponse = new ChatUserInfoResponse();
            ChatUser chatUser = userService.selectChatUserByNo(chatUserDepartment.getUserNo());
            ChatUserInfo chatUserInfo = userInfoService.selectByUserNo(chatUserDepartment.getUserNo());
            BeanUtils.copyProperties(chatUser, chatUserInfoResponse);
            BeanUtils.copyProperties(chatUserInfo, chatUserInfoResponse);
            chatUserInfoResponse.setCompanyDepartment(departmentName);
            userInfoTreeMap.put(chatUserDepartment.getUserNo(), chatUserInfoResponse);
        });
        return new DepartmentMemberResponse(departmentNo, userInfoTreeMap);
    }


}
