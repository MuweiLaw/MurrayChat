package com.murray.server;

import com.murray.dao.ChatUserDao;
import com.murray.entity.ChatUser;
import com.murray.entity.example.ChatUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Murray Law
 * @describe 聊天用户服务
 * @createTime 2020/10/23
 */
@Service
public class ChatUserService {
    @Autowired
    private ChatUserDao chatUserDao;

    private ChatUserExample chatUserExample=new ChatUserExample();

    /**
     * @param ChatUserNO
     * @return com.murray.entity.ChatUser
     * @description 根据用户编号查询一条记录
     * @author Murray Law
     * @date 2020/12/31 7:26
     */
    public ChatUser selectChatUserByNo(String ChatUserNO) {
        ChatUserExample chatUserExample = new ChatUserExample();
        chatUserExample.createCriteria().andChatUserNoEqualTo(ChatUserNO);
        List<ChatUser> chatUsers = chatUserDao.selectByExample(chatUserExample);
        if (chatUsers.size() < 1) {
            return null;
        } else {
            return chatUsers.get(0);
        }
    }

    /**
     * @param chatUser
     * @return int
     * @description 插入用户
     * @author Murray Law
     * @date 2020/12/31 7:37
     */
    public int insert(ChatUser chatUser) {
        return chatUserDao.insert(chatUser);
    }
    /**
     * @description 根据用户编号修改信息
     * @author Murray Law
     * @date 2020/12/31 7:43
     * @param chatUser
     * @param userNo
     * @return int
     */
    public int updateByUserNoAndPwd(ChatUser chatUser,String userNo,String pwd){
        chatUserExample.clear();
        chatUserExample.createCriteria().andChatUserNoEqualTo(userNo).andPasswordEqualTo(pwd);
        return chatUserDao.updateByExampleSelective( chatUser,chatUserExample);
    }
}
