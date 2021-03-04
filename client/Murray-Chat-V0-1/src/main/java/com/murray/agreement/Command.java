package com.murray.agreement;

/**
 * @author Murray Law
 * @describe 通信协议包含的指令
 * @createTime 2020/10/25
 */
public interface Command {

    Byte
            SERVER_BASIC_RESPONSE = 0,
            LOGIN_REQUEST = 1, LOGIN_RESPONSE = 2,//登录的请求和响应
            MESSAGE = 3, MESSAGE_RESPONSE = 4,//消息的请求和响应
            HAVE_READ_REQUEST = 5, HAVE_READ_RESPONSE = 6,//已读取消息/下载文件 的请求和响应
            FRIEND_REQUEST = 7, FRIEND_RESPONSE = 8,//好友请求和响应
            FILE = 9,//文件类型
            SERVER_RECEIVED_RESPONSE = 10,//服务器收到文件的请求和响应
            CREATE_GROUP_REQUEST = 11, CREATE_GROUP_RESPONSE = 12,//创建群聊的请求和响应
            GROUP_CHAT_REQUEST = 13, GROUP_CHAT_RESPONSE = 14,//群聊消息的请求和响应
            MAIL_REQUEST = 15, MAIL_RESPONSE = 16,//邮件的请求和响应
            CHAT_USER_INFO_REQUEST = 17, CHAT_USER_INFO_RESPONSE = 18,//聊天用户的信息请求和响应
            CHAT_GROUP_INFO_REQUEST = 19, CHAT_GROUP_INFO_RESPONSE = 20,//群组信息的请求和响应
            AVATAR_REQUEST = 21, AVATAR = 22,//头像请求下载和响应
            GROUP_BASIC_REQUEST = 23, GROUP_BASIC_RESPONSE = 24,//群组信息请求下载和发送
            MEETING_REMIND_REQUEST = 25, MEETING_REMIND_RESPONSE = 26,//会议提醒请求和响应
            MEETING_TABLE_REQUEST = 27, MEETING_TABLE_RESPONSE = 28,//会议表格数据请求和响应
            DEPARTMENT_REQUEST = 29, DEPARTMENT_RESPONSE = 30,//部门基本信息请求和响应
            ADDRESS_BOOK_REQUEST = 31, ADDRESS_BOOK_RESPONSE = 32,//通讯录信息请求和响应
            DEPARTMENT_MEMBER_REQUEST = 33, DEPARTMENT_MEMBER_RESPONSE = 34,//通讯录部门成员信息请求和响应
            GROUP_MSG_HAVE_READ_CHAT_RESPONSE = 35,//群组消息已读取聊天响应
            OPERATE_GROUP_REQUEST = 36,//操作群组请求
            CHANGE_PASSWORD_REQUEST = 37, //修改密码请求
    CHANGE_PASSWORD_RESPONSE = 38;
}
