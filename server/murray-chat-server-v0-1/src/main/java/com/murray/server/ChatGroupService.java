package com.murray.server;

import com.murray.dao.ChatGroupDao;
import com.murray.dto.response.GroupBasic;
import com.murray.dto.response.GroupBasicResponse;
import com.murray.entity.ChatGroup;
import com.murray.entity.example.ChatGroupExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Murray Law
 * @describe 群组管理服务
 * @createTime 2020/11/10
 */
@Service
public class ChatGroupService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ChatGroupDao chatGroupDao;
    @Autowired
    private ChatGroupRoleService groupRoleService;
    /**
     * 查询的例子
     */
    private ChatGroupExample groupExample = new ChatGroupExample();

    /**
     * @param chatGroup 实体
     * @return int
     * @description 插入一个群聊
     * @author Murray Law
     * @date 2020/12/8 7:53
     */
    public int insert(ChatGroup chatGroup) {
        return chatGroupDao.insert(chatGroup);
    }

    /**
     * @param requesterNo
     * @return com.murray.dto.response.GroupBasicResponse
     * @description 根据用户编号找所有群组信息并响应
     * @author Murray Law
     * @date 2020/12/25 20:21
     */
    public GroupBasicResponse selectByUserNo(String requesterNo) {
        Map<String, GroupBasic> groupBasicMap = new TreeMap<>();
        //先查询群组记录
        groupRoleService.selectByUserNo(requesterNo).forEach(groupUserRole -> {
            groupExample.clear();
            groupExample.createCriteria().andGroupNoEqualTo(groupUserRole.getGroupNo());
            //查出群组信息
            List<ChatGroup> chatGroups = chatGroupDao.selectByExample(groupExample);
            if (!chatGroups.isEmpty()) {
                ChatGroup chatGroup = chatGroups.get(0);
                GroupBasic groupBasic = new GroupBasic(chatGroup.getGroupNo(),
                        chatGroup.getGroupName(),
                        stringRedisTemplate.opsForValue().get(chatGroup.getGroupNo()),
                        groupRoleService.selectUserRoleMapByGroupNo(groupUserRole.getGroupNo())//查出群组的人员角色
                );
                groupBasicMap.put(chatGroup.getGroupNo(), groupBasic);
            }
        });
        return new GroupBasicResponse(requesterNo, groupBasicMap);
    }

    /**
     * @param groupNo
     * @param groupName
     * @return int
     * @description 按组号和组名更新
     * @author Murray Law
     * @date 2020/12/25 20:31
     */
    public int updateByGroupNoAndGroupName(String groupNo, String groupName) {
        groupExample.clear();
        groupExample.createCriteria().andGroupNoEqualTo(groupNo);
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setGroupName(groupName);
       return chatGroupDao.updateByExampleSelective(chatGroup, groupExample);
    }
}
