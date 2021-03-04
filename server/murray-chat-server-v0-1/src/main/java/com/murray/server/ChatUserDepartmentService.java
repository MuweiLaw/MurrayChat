package com.murray.server;

import com.murray.dao.ChatUserDepartmentDao;
import com.murray.entity.ChatUserDepartment;
import com.murray.entity.example.ChatUserDepartmentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Murray Law
 * @describe 用户和部门服务
 * @createTime 2020/12/8
 */
@Service
public class ChatUserDepartmentService {
    @Autowired
    private ChatUserDepartmentDao userDepartmentDao;
    private ChatUserDepartmentExample userDepartmentExample = new ChatUserDepartmentExample();

    /**
     * @param departmentNo 部门编号
     * @return java.lang.Long
     * @description 获取部门的用户总数
     * @author Murray Law
     * @date 2020/12/8 8:01
     */
    public Long countByDepartmentNo(String departmentNo) {
        userDepartmentExample.clear();
        userDepartmentExample.createCriteria().andDepartmentNoEqualTo(departmentNo);
        return userDepartmentDao.countByExample(userDepartmentExample);
    }

    /**
     * @param departmentNo
     * @return java.util.List<com.murray.entity.ChatUserDepartment>
     * @description 根据部门编号查找记录
     * @author Murray Law
     * @date 2020/12/8 22:49
     */
    public List<ChatUserDepartment> selectByDepartmentNo(String departmentNo) {
        userDepartmentExample.clear();
        userDepartmentExample.createCriteria().andDepartmentNoEqualTo(departmentNo);
        return userDepartmentDao.selectByExample(userDepartmentExample);
    }
    public String selectDepartmentNameByUserNo(String userNo) {
        return userDepartmentDao.selectDepartmentNameByUserNo(userNo);
    }
}
