package com.murray.agreement.impl;

import com.murray.agreement.Serializer;
import com.murray.agreement.SerializerAlgorithm;
import com.murray.dto.response.ServerReceivedResponse;
import com.murray.entity.FilePacket;
import com.murray.entity.PrivateChatMessage;
import com.murray.server.ManageSpringBeans;
import com.murray.server.netty.ServerPacketCodeCompile;
import com.murray.utils.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import static com.murray.config.ServerCache.SERVER_FILE_SAVE_PATH;

/**
 * @author Murray Law
 * @describe 服务端 文件序列化算法实现
 * @createTime 2020/11/3
 */
public class FileSerializer implements Serializer {
    //注入Redis
    private RedisTemplate redisTemplate = ManageSpringBeans.getBean(RedisTemplate.class);
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.FILE;

    }

    @Override
    public byte[] serialize(Object object) {
        FilePacket filePacket = (FilePacket) object;
        //创建源（文件字节输入流的）
        File file = filePacket.getFile();
        //和消息编号关联
        byte[] uuid = filePacket.getFileName().getBytes(StandardCharsets.UTF_8);
        //选择流
        InputStream is = null;
        ByteArrayOutputStream bAOS;
        try {
            is = new FileInputStream(file);
            bAOS = new ByteArrayOutputStream();
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                bAOS.write(flush, 0, len);
            }
            bAOS.flush();
            byte[] fileByte = bAOS.toByteArray();
            //文件信息相加
            byte[] bytes = new byte[36 + fileByte.length];
            System.arraycopy(uuid, 0, bytes, 0, 36);
            System.arraycopy(fileByte, 0, bytes, 36, fileByte.length);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件字节输入流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        new Thread(() -> {
            String uuid;
            byte[] uuidBytes = new byte[36];
            System.arraycopy(bytes, 0, uuidBytes, 0, 36);
            uuid = new String(uuidBytes, StandardCharsets.UTF_8);
//            if (CHAT_MESSAGE_MAP.containsKey(uuid)) {
                if (redisTemplate.opsForHash().hasKey(PrivateChatMessage.PRIVATE_MSG +uuid,"msgNo")) {
                    writeMsgFile(bytes, uuid);
            } else {
                writeFile(bytes, uuid);
            }
        }).start();
        return null;
    }

    private void writeMsgFile(byte[] bytes, String uuid) {
        byte[] fileBytes = new byte[bytes.length - 36];
        System.arraycopy(bytes, 36, fileBytes, 0, fileBytes.length);
        //获取发送者的Chanel,准备显示进度
//        String chatUserNO = CHAT_MESSAGE_MAP.get(uuid).getUserNo();
        String chatUserNO = (String) redisTemplate.opsForHash().get(PrivateChatMessage.PRIVATE_MSG+uuid,"senderUserNo");

        Channel senderChannel = SessionUtil.getChannel(chatUserNO);
        ServerReceivedResponse serverReceivedResponse = new ServerReceivedResponse(uuid, (byte) 0, "uploading");
        //创建源
        File file = new File(SERVER_FILE_SAVE_PATH + uuid);
        ByteArrayInputStream bais;
        OutputStream os = null;
        try {
            bais = new ByteArrayInputStream(fileBytes);
            os = new FileOutputStream(file);
            byte[] flush = new byte[1024];
            int len = -1;
            int count = 0;
            while ((len = bais.read(flush)) != -1) {
                os.write(flush, 0, len);
                os.flush();
                count += 1;
                //每写入10兆,发送一次进度消息
                if (count == 10240) {
                    //给文件发送者发送百分比进度
                    double doublePercentage = (file.length() * 100.00) / (bytes.length * 100.00) * 100;
                    Byte percentage = (byte) doublePercentage;
                    serverReceivedResponse.setPercentage(percentage);
                    senderChannel.writeAndFlush(ServerPacketCodeCompile.encode(serverReceivedResponse));
                    count = 0;
                }
            }
            //序列化百分比
            DecimalFormat df = new DecimalFormat("#.00");
            String format = df.format((file.length() * 100.00) / ((bytes.length - 36) * 100.00) * 100);
            //写入完成给文件发送者发送完成进度
            if (format.equals("100.00")) {
                serverReceivedResponse.setPercentage((byte) 100);
                serverReceivedResponse.setStatus("success");
            } else {
                serverReceivedResponse.setStatus("fail");
            }
            senderChannel.writeAndFlush(ServerPacketCodeCompile.encode(serverReceivedResponse));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeFile(byte[] bytes, String uuid) {
        byte[] fileBytes = new byte[bytes.length - 36];
        System.arraycopy(bytes, 36, fileBytes, 0, fileBytes.length);
        //创建源
        File file = new File(SERVER_FILE_SAVE_PATH + uuid);
        ByteArrayInputStream bais;
        OutputStream os = null;
        try {
            bais = new ByteArrayInputStream(fileBytes);
            os = new FileOutputStream(file);
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = bais.read(flush)) != -1) {
                os.write(flush, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
