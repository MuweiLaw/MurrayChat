package com.murray.agreement;


/**
 * @author Murray Law
 * @describe 类型
 * @createTime 2020/11/16
 */
public interface MessageType {
    Byte PRIVATE_CHAT_TYPE = 0, //私聊消息类型
            GROUP_CHAT_TYPE = 1,//群聊类型
            PRIVATE_FILE_TYPE = 2,//私聊文件类型
            GROUP_FILE_TYPE = 3,//群聊消息类型
            PRIVATE_MAIL_TYPE = 4,//私信邮箱类型
            GROUP_MAIL_TYPE = 5;//群邮箱类型


}
