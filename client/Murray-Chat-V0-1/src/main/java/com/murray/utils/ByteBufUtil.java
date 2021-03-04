package com.murray.utils;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author Murray Law
 * @describe 字节缓冲区的工具类
 * @createTime 2020/10/25
 */
public  class ByteBufUtil {
    public static ByteBuf getByteBuf(Object object) {
        // 1. 获取二进制抽象 ByteBuf
//        ByteBuf byteBuf = ctx.alloc().buffer();
        ByteBuf byteBuf= ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 准备数据，指定字符串的字符集为 utf-8
//        byte[] bytes = json.getBytes(Charset.forName("utf-8"));
        byte[] bytes = JSON.toJSONBytes(object);
        // 3. 填充数据到 ByteBuf
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
