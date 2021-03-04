package com.murray.handler;

import com.murray.agreement.Packet;
import com.murray.agreement.Serializer;
import com.murray.agreement.impl.AvatarSerializer;
import com.murray.agreement.impl.FileSerializer;
import com.murray.agreement.impl.JSONSerializer;
import com.murray.dto.request.*;
import com.murray.dto.response.*;
import com.murray.entity.AvatarPacket;
import com.murray.entity.FilePacket;
import com.murray.entity.PrivateChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author Murray Law
 * @describe Java 对象与二进制数据的互转
 * @createTime 2020/10/25
 */
public class ClientPacketCodeCompile {
    public static final int MAGIC_NUMBER = 0x19981102;

    /**
     * @param packet
     * @return io.netty.buffer.ByteBuf
     * @description 封装成二进制的过程
     * @author Murray Law
     * @date 2020/10/25 10:11
     */
    public static ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes;

        // 3. 实际编码过程

        //3.1 写入魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        //3.2 写入协议版本号
        byteBuf.writeByte(packet.getVersion());
        //3.3 写入序列化方式,默认json
        byte serializerAlgorithm = Serializer.DEFAULT.getSerializerAlgorithm();
        //3.3 写入序列化方式,默认
        if (packet instanceof FilePacket) {
            bytes = Serializer.FILE.serialize(packet);
            serializerAlgorithm = Serializer.FILE.getSerializerAlgorithm();

        } else if (packet instanceof AvatarPacket) {
            bytes = Serializer.AVATAR.serialize(packet);
            serializerAlgorithm = Serializer.AVATAR.getSerializerAlgorithm();
        } else {
            bytes = Serializer.DEFAULT.serialize(packet);
        }
        byteBuf.writeByte(serializerAlgorithm);
        //3.4  写入消息类型类型
        byteBuf.writeByte(packet.getCommand());
        //3.5 写入包的字节长度
        byteBuf.writeInt(bytes.length);


        //写入字节码
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * @param byteBuf
     * @return com.murray.agreement.Packet
     * @description 解析 Java 对象的过程
     * @author Murray Law
     * @date 2020/10/25 10:11
     */
    public static Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private static Class<? extends Packet> getRequestType(byte command) {
        switch (command) {
            case 0:
                return ServerBasicResponse.class;
            case 1:
                return LoginRequest.class;
            case 2:
                return LoginResponse.class;
            case 3:
                return PrivateChatMessage.class;
            case 4:
                return PrivateChatMessage.class;
            case 5:
                return HaveReadRequest.class;
            case 6:
                return HaveReadResponse.class;
            case 7:
                return ChatFriendRequest.class;
            case 8:
                return ChatFriendResponse.class;
            case 9:
                return FilePacket.class;
            case 10:
                return ServerReceivedResponse.class;
            case 11:
                return CreateGroupRequest.class;
            case 12:
                return CreateGroupResponse.class;
            case 13:
                return GroupMsgRequest.class;
            case 14:
                return GroupMsgResponse.class;
            case 15:
                return MailRequest.class;
            case 16:
                return MailResponse.class;
            case 17:
                return ChatUserInfoRequest.class;
            case 18:
                return ChatUserInfoResponse.class;
            case 21:
                return AvatarRequest.class;
            case 22:
                return AvatarPacket.class;
            case 23:
                return GroupBasicRequest.class;
            case 24:
                return GroupBasicResponse.class;
            case 25:
                return MeetingReminderRequest.class;
            case 26:
                return MeetingReminderResponse.class;
            case 27:
                return MeetingTableRequest.class;
            case 28:
                return MeetingTableResponse.class;
            case 29:
                return DepartmentRequest.class;
            case 30:
                return DepartmentResponse.class;
            case 31:
                return AddressBookRequest.class;
            case 32:
                return AddressBookResponse.class;
            case 33:
                return DepartmentMemberRequest.class;
            case 34:
                return DepartmentMemberResponse.class;
            case 35:
                return GroupMsgHaveReadResponse.class;
            case 36:
                return OperateGroupRequest.class;
            case 37:
                return ChangePwdRequest.class;
            default:
                System.out.println("无法解码的JAVA对象");
                return null;
        }

    }

    private static Serializer getSerializer(byte serializeAlgorithm) {
        if (1 == serializeAlgorithm) {
            return new JSONSerializer();
        }
        if (2 == serializeAlgorithm) {
            return new FileSerializer();
        }
        if (3 == serializeAlgorithm) {
            return new AvatarSerializer();
        }
        return null;
    }

}
