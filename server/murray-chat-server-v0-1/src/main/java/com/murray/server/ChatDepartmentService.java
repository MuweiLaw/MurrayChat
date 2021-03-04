package com.murray.server;

import com.murray.dao.ChatDepartmentDao;
import com.murray.entity.ChatDepartment;
import com.murray.entity.example.ChatDepartmentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Murray Law
 * @describe 部门服务
 * @createTime 2020/12/8
 */
@Service
public class ChatDepartmentService {
    @Autowired
    private ChatDepartmentDao chatDepartmentDao;
    private ChatDepartmentExample chatDepartmentExample = new ChatDepartmentExample();

    /**
     * @param parent 父级部门编号或公司编号
     * @return java.lang.Long
     * @description 查询改公司下/部门下的子部门
     * @author Murray Law
     * @date 2020/12/8 8:17
     */
    public List<ChatDepartment> selectByParent(String parent) {
        chatDepartmentExample.clear();
        chatDepartmentExample.createCriteria().andParentEqualTo(parent);
        return chatDepartmentDao.selectByExample(chatDepartmentExample);
    }
}
