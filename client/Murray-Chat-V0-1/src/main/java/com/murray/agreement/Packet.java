package com.murray.agreement;

/**
 * @author Murray Law
 * @describe 自定义的通信协议
 * @createTime 2020/10/25
 */
public abstract class Packet {
    /**
     * 协议版本
     */
    public Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();

    public Packet() {
    }

    public Packet(Byte version) {
        this.version = version;
    }

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}