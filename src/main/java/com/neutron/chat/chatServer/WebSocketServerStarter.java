package com.neutron.chat.chatServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * websocket服务器启动类，与springboot主启动类并行启动
 *
 * @author zzs
 * @date 2023/5/25 21:30
 */
@Component
public class WebSocketServerStarter implements CommandLineRunner {

    @Resource
    private WebSocketServer webSocketServer;

    @Value("${websocket.port}")
    private Integer port;

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.run(port);
    }
}
