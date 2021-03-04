package com.murray.agreement.impl;

import com.murray.agreement.Serializer;
import com.murray.agreement.SerializerAlgorithm;
import com.murray.entity.FilePacket;
import com.murray.view.vo.cell.GroupChatCellRenderer;
import com.murray.view.vo.cell.PrivateChatCellRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import static com.murray.cache.ClientCache.*;

/**
 * @author Murray Law
 * @describe 客户端 文件序列化算法实现
 * @createTime 2020/11/3
 */
public class FileSerializer implements Serializer {
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
        byte[] uuid = filePacket.getFileNo().getBytes(StandardCharsets.UTF_8);
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
            //获取当时填写的文件路径
            File file = new File(fileSavePath);
            //获得消息单元,文件的UUID和消息单元格是一致的
            PrivateChatCellRenderer privateChatCellRenderer = null;
            GroupChatCellRenderer groupChatCellRenderer = null;
            if (PRIVATE_CHAT_MESSAGE_MAP.containsKey(fileSaveUUID)) {
                privateChatCellRenderer = CHAT_MAIN_FRAME.privateMSGPanel.cellMap.get(fileSaveUUID);
            } else {
                groupChatCellRenderer = CHAT_MAIN_FRAME.groupMSGPanel.cellMap.get(fileSaveUUID);
            }

            ByteArrayInputStream bais;
            OutputStream os = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                os = new FileOutputStream(file);
                byte[] flush = new byte[1024];
                int len = -1;
                int count = 0;
                while ((len = bais.read(flush)) != -1) {
                    os.write(flush, 0, len);
                    count += 1;
                    //每写入10兆,发送一次进度消息
                    if (count == 5120) {
                        //给文件发送者发送百分比进度
                        double doublePercentage = (file.length() * 100.00) / (bytes.length * 100.00) * 100;
                        byte percentage = (byte) doublePercentage;
                        //更改进度条
                        if (privateChatCellRenderer != null) {
                            privateChatCellRenderer.progressBar.setValue(percentage);
                        } else if (groupChatCellRenderer != null) {
                            groupChatCellRenderer.progressBar.setValue(percentage);
                        }
                        count = 0;
                    }
                }
                os.flush();
                //序列化百分比
                DecimalFormat df = new DecimalFormat("#.00");
                String format = df.format((file.length() * 100.00) / ((bytes.length) * 100.00) * 100);
                //写入完成给文件发送者发送完成进度
                if (format.equals("100.00")) {
                    if (privateChatCellRenderer != null) {
                        privateChatCellRenderer.progressBar.setValue(100);
                    }
                    if (groupChatCellRenderer != null) {
                        groupChatCellRenderer.progressBar.setValue(100);
                    }
                }
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
