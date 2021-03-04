package com.murray.agreement.impl;

import com.murray.agreement.Serializer;
import com.murray.agreement.SerializerAlgorithm;
import com.murray.entity.AvatarPacket;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Murray Law
 * @describe 服务端 文件序列化算法实现
 * @createTime 2020/11/3
 */
@Component
public class AvatarSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.AVATAR;

    }

    @Override
    public byte[] serialize(Object object) {
        AvatarPacket avatarPacket = (AvatarPacket) object;
        //创建源（文件字节输入流的）
        File avatar = avatarPacket.getAvatar();
        byte[] nameBytes= avatar.getName().getBytes(StandardCharsets.UTF_8);
        byte[] nameLen=new  byte[4];
        nameLen[0] = (byte)nameBytes.length;
        nameLen[1] = (byte) (nameBytes.length >> 8);
        nameLen[2] = (byte) (nameBytes.length >> 16);
        nameLen[3] = (byte) (nameBytes.length >> 24);




        //选择流
        InputStream is = null;
        ByteArrayOutputStream bAOS;

        try {
            is = new FileInputStream(avatar);
            bAOS = new ByteArrayOutputStream();
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                bAOS.write(flush, 0, len);
            }
            bAOS.flush();
            byte[] bytes = bAOS.toByteArray();

            byte[] finalBytes=new byte[4+nameBytes.length+bytes.length];

            System.arraycopy(nameLen, 0, finalBytes, 0, 4);
            System.arraycopy(nameBytes, 0, finalBytes, 4, nameBytes.length);
            System.arraycopy(bytes, 0, finalBytes, 4+nameBytes.length, bytes.length);

            return finalBytes;
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
            //获取文件名称长度
            byte[] nameLenBytes=new byte[4];
            System.arraycopy(bytes, 0, nameLenBytes, 0, nameLenBytes.length);
            //转int获得名称转字节数组的长度
            int fileLen = 0;
            for(int i = 0; i < 4 ; i++){
                fileLen += nameLenBytes[i] << i*8;
            }
            //创建名称的自己数组
            byte[] nameBytes = new byte[fileLen];
            System.arraycopy(bytes, 4, nameBytes, 0,fileLen);
            //获得文件名
            String fileName = new String(nameBytes, StandardCharsets.UTF_8);
            //获取文件内容
            byte[] fileBytes=new byte[bytes.length-4-fileLen];
            System.arraycopy(bytes, 4+fileLen, fileBytes, 0,fileBytes.length);

            //获取当时填写的文件路径
            File file = new File(System.getProperty("user.dir")+"\\avatar\\"+fileName);
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
        }).start();
        return null;
    }


}
