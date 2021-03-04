package com.murray.server;

import com.murray.dto.request.AddressBookRequest;
import com.murray.dto.response.AddressBookResponse;
import com.murray.dto.response.DepartmentResponse;
import com.murray.entity.ChatDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeMap;

/**
 * @author Murray Law
 * @describe 通讯录服务
 * @createTime 2020/12/8
 */
@Service
public class AddressBookService {
    @Autowired
    private ChatFriendsService friendsService;
    @Autowired
    private ChatGroupRoleService groupRoleService;
    @Autowired
    private ChatDepartmentService departmentService;
    @Autowired
    private ChatUserDepartmentService userDepartmentService;

    /**
     * @param request 通讯录请求信息
     * @return com.murray.dto.response.AddressBookResponse
     * @description 获取通讯录基本信息
     * @author Murray Law
     * @date 2020/12/8 8:55
     */
    @Transactional
    public AddressBookResponse getAddressBookBasic(AddressBookRequest request) {
        TreeMap<String, DepartmentResponse> departmentMap = new TreeMap<>();
        Long friendSum = friendsService.countByUserNo(request.getRequester());
        Long groupSum = groupRoleService.countByUserNo(request.getRequester());
        List<ChatDepartment> chatDepartments = departmentService.selectByParent(request.getCompany());
        chatDepartments.forEach(chatDepartment -> {
            Long departmentUserSum = userDepartmentService.countByDepartmentNo(chatDepartment.getDepartmentNo());
            departmentMap.put(chatDepartment.getName(),new DepartmentResponse(chatDepartment.getDepartmentNo(),chatDepartment.getName(),departmentUserSum));
        });
        AddressBookResponse addressBookResponse = new AddressBookResponse(friendSum, groupSum, departmentMap);

        return addressBookResponse;
    }
}
