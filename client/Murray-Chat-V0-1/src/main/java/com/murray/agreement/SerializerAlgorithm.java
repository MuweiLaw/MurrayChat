package com.murray.agreement;

/**
 * @author Murray Law
 * @describe 序列化算法标识
 * @createTime 2020/10/25
 */
public interface SerializerAlgorithm {
    /**
     * json 序列化标识
     */
    byte JSON = 1;
    /**
     * 文件 序列化标识
     */
    byte FILE = 2;
    /**
     * 头像 序列化标识
     */
    byte AVATAR = 3;
}
