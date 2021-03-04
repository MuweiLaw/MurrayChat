package com.murray.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author Murray Law
 * @describe netty的启动服务
 * @createTime 2020/10/22
 */
@Component
public class NettyServer {
    @Value("${netty.port}")
    private  int port=8888;

     private EventLoopGroup bossGroup = new NioEventLoopGroup(1);  //处理请求连接的线程组
     private EventLoopGroup workGroup = new NioEventLoopGroup();
    /**
     * @return void
     * @description netty服务的启动方法,       @PostConstruct必须要有
     * @author Murray Law
     * @date 2020/10/22 23:35
     */
//    @PostConstruct
    public void start() {
                 // 处理读写业务的线程组 默认为CPU核数的两倍

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup) //将两个线程组加到服务器
                    .channel(NioServerSocketChannel.class) //指定通道类型
                    .option(ChannelOption.SO_BACKLOG, 20) // 连接等待队列大小
//                    .option(ChannelOption.TCP_NODELAY, true)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)// 启用心跳保活机制
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            //拆包粘包处理
                            ch.pipeline().addLast(new Spliter());
                            ch.pipeline().addLast(new ServerByteBufHandler());
//                            ch.pipeline().addLast(new FileUploadServerHandler());

                        }
                    });

            ChannelFuture future = serverBootstrap.bind("127.0.0.1",port).sync();// 开启服务
            if (future.isSuccess()) {
                System.out.println("启动 Netty 成功");
            }
            future.channel().closeFuture().sync(); //监听关闭

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 非常优雅的关闭资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workGroup.shutdownGracefully().syncUninterruptibly();
        System.out.println("关闭 Netty 成功");
    }
}
