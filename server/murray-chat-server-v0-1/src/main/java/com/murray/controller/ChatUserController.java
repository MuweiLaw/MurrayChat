package com.murray.controller;

import com.murray.entity.ChatUser;
import com.murray.server.ChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Murray Law
 * @describe 聊天用户的控制器
 * @createTime 2020/10/23
 */
@RestController
@RequestMapping("/")
public class ChatUserController {
    @Autowired
    private ChatUserService chatUserService;

    @GetMapping("/selectChatUserById/{ChatUserNO}")
    public ChatUser selectChatUserById(@PathVariable("ChatUserNO") String ChatUserNO) {
        return chatUserService.selectChatUserByNo(ChatUserNO);
    }
    @GetMapping("/hello")
    public String hello() {
        return "chatUserService.selectByNme(name)";
    }
}
