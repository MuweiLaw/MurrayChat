package com.murray.agreement.impl;

import com.alibaba.fastjson.JSON;
import com.murray.agreement.Serializer;
import com.murray.agreement.SerializerAlgorithm;

/**
 * @author Murray Law
 * @describe JSON序列化算法实现
 * @createTime 2020/10/25
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }

}
