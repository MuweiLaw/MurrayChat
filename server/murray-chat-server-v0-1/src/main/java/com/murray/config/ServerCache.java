package com.murray.config;

import org.springframework.stereotype.Component;

/**
 * @author Murray Law
 * @describe 在线用户的缓存 TODO 这个类放到redis中缓存
 * @createTime 2020/10/25
 */
@Component
public class ServerCache {
    //文件的保存路径
    public static  final String SERVER_FILE_SAVE_PATH="C:\\Users\\Murray\\Downloads\\Server\\files\\";
    //头像的保存路径
    public static  final String SERVER_AVATAR_SAVE_PATH="C:\\Users\\Murray\\Downloads\\Server\\avatar\\";

}
