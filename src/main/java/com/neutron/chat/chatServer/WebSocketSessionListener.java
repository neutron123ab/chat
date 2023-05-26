package com.neutron.chat.chatServer;

import com.neutron.chat.model.dto.UserDTO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author zzs
 * @date 2023/5/25 21:48
 */
@Slf4j
public class WebSocketSessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        UserDTO loginUser = (UserDTO) se.getSession().getAttribute("user_login_state");
        Long userId = loginUser.getId();
        ChannelId channelId = ChatHandler.userChannelMap.get(userId);
        //当用户退出登录时，移除对应的channel
        ChatHandler.userChannelMap.remove(userId);
        Channel channel = ChatHandler.channelGroup.find(channelId);
        ChatHandler.channelGroup.remove(channel);
        log.info("客户端 {} 与服务器断开连接", channelId);
    }
}
