package com.murray.agreement;

import com.murray.agreement.impl.AvatarSerializer;
import com.murray.agreement.impl.FileSerializer;
import com.murray.agreement.impl.JSONSerializer;

/**
 * @author Murray Law
 * @describe 自定义序列化接口
 * @createTime 2020/10/25
 */
public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();
    Serializer FILE = new FileSerializer();
    Serializer AVATAR = new AvatarSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
