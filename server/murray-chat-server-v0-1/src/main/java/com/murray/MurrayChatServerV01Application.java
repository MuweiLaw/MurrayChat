package com.murray;

import com.murray.server.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class MurrayChatServerV01Application {
    public static void main(String[] args) {
        SpringApplication.run(MurrayChatServerV01Application.class, args);
        new NettyServer().start();
    }

}
