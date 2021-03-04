package com.murray.server;

import com.murray.dao.ChatGroupUserRoleDao;
import com.murray.entity.ChatGroupUserRole;
import com.murray.entity.example.ChatGroupUserRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Murray Law
 * @describe 群组管理服务
 * @createTime 2020/11/10
 */
@Service
@CacheConfig(cacheNames = "chatGroupUserRole")
public class ChatGroupRoleService {
    @Autowired
    private ChatGroupUserRoleDao chatGroupUserRoleDao;
    private ChatGroupUserRoleExample groupUserRoleExample = new ChatGroupUserRoleExample();

    /**
     * @param record 记录
     * @return int
     * @description 插入一条记录
     * @author Murray Law
     * @date 2020/12/8 7:58
     */
    public int insert(ChatGroupUserRole record) {
        return chatGroupUserRoleDao.insert(record);
    }

    /**
     * @param userNo 用户编号
     * @return java.lang.Long
     * @description 获取群组总数
     * @author Murray Law
     * @date 2020/12/8 8:01
     */
    public Long countByUserNo(String userNo) {
        groupUserRoleExample.clear();
        groupUserRoleExample.createCriteria().andUserNoEqualTo(userNo);
        return chatGroupUserRoleDao.countByExample(groupUserRoleExample);
    }

    public Map<String, Byte> selectUserRoleMapByGroupNo(String groupNo) {
        Map<String, Byte> groupUserRoleMap = new HashMap<>();
        groupUserRoleExample.clear();
        groupUserRoleExample.createCriteria().andGroupNoEqualTo(groupNo);
        chatGroupUserRoleDao.selectByExample(groupUserRoleExample).forEach(groupUserRole -> {
            groupUserRoleMap.put(groupUserRole.getUserNo(), groupUserRole.getRole());
        });
        return groupUserRoleMap;
    }

    @Cacheable()
    public List<ChatGroupUserRole> selectBasicAll() {
        return chatGroupUserRoleDao.selectBasicAll();
    }

    @Cacheable()
    public List<String> selectUserNoByGroupNo(String groupNo) {
        return chatGroupUserRoleDao.selectUserNoByGroupNo(groupNo);
    }

    /**
     * @param chatGroupUserRoles 群组用户角色列表
     * @return int
     * @description 批量插入
     * @author Murray Law
     * @date 2020/11/24 16:54
     */
    public int insertList(List<ChatGroupUserRole> chatGroupUserRoles) {
        return chatGroupUserRoleDao.insertList(chatGroupUserRoles);
    }

    /**
     * @param userNo
     * @return java.util.List<com.murray.entity.ChatGroupUserRole>
     * @description 根据用户编号, 查询记录
     * @author Murray Law
     * @date 2020/12/9 11:57
     */
    public List<ChatGroupUserRole> selectByUserNo(String userNo) {
        groupUserRoleExample.clear();
        groupUserRoleExample.createCriteria().andUserNoEqualTo(userNo);
        return chatGroupUserRoleDao.selectByExample(groupUserRoleExample);
    }

    public int deleteByGroupNoAndUserNo(String groupNo, String userNo) {
        groupUserRoleExample.clear();
        groupUserRoleExample.createCriteria().andGroupNoEqualTo(groupNo).andUserNoEqualTo(userNo);
       return chatGroupUserRoleDao.deleteByExample(groupUserRoleExample);
    }
}
