package com.murray.handler;

import com.murray.cache.ClientCache;
import com.murray.dto.request.AvatarRequest;
import com.murray.dto.request.ChatUserInfoRequest;
import com.murray.dto.request.GroupMsgRequest;
import com.murray.entity.PrivateChatMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author Murray Law
 * @describe 登陆时的处理类
 * @createTime 2020/10/23
 */
public class LoginHandler {


    /**
     * @return void
     * @description 去连接服务器
     * @author Murray Law
     * @date 2020/10/21 23:42
     */
    public void doConnect(String host, int port) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //粘包拆包处理器
                        ch.pipeline().addLast(new Spliter());
                        // 收消息处理器
                        ch.pipeline().addLast(new ClientByteBufHandler());
//                        ch.pipeline().addLast(new FileUploadClientHandler(filePacket));
                    }
                });
        //指定域名和端口连接
        ChannelFuture future = bootstrap.connect(host, port);
        ClientCache.CHANNEL=future.channel();
        // 连接成功之后，启动控制台线程
        future.addListener(future1 -> {
            if (future.isSuccess()){
               ClientCache.CHANNEL = ((ChannelFuture) future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread();

            }
        });

//todo 监听连接状态

    }

    //调用上述方法
    public void doConnectByDEV() {
        doConnect("127.0.0.1", 8888);
        //doConnect("10.253.30.5", 8888);
        //doConnect("10.217.242.49", 8888);
    }


    private static void startConsoleThread( ) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("输入消息发送至服务端: ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                List<String> strings = new ArrayList<>();
                strings.add("a");
                PrivateChatMessage privateChatMessage = new PrivateChatMessage("2562758836",line,"15879410045",new Date(),false,(byte)0);

                GroupMsgRequest groupMsgRequest = new GroupMsgRequest("368493ff-07e4-4e12-9c2d-106c85e749f2","2562758836",strings,line,new Date(),(byte)1);
                AvatarRequest avatarRequest = new AvatarRequest("a","2562758836");
                ChatUserInfoRequest chatUserInfoRequest = new ChatUserInfoRequest("a","2562758836");
                ByteBuf encode = ClientPacketCodeCompile.encode(privateChatMessage);
                ClientCache.CHANNEL.writeAndFlush(encode);
            }
        }).start();

    }
}