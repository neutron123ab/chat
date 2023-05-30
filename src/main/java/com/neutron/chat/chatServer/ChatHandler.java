package com.neutron.chat.chatServer;

import com.google.gson.Gson;
import com.neutron.chat.model.entity.ChatMessage;
import com.neutron.chat.service.GroupService;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.neutron.chat.constants.ChatConstants.*;

/**
 * @author zzs
 * @date 2023/5/25 19:28
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 保存所有当前已登录的用户channel
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 将用户id与用户channelId绑定，便于快速找到对应的channel
     */
    public static ConcurrentMap<Long, ChannelId> userChannelMap = new ConcurrentHashMap<>();

    /**
     * 将群聊id与群聊对应的channelGroup绑定
     */
    public static ConcurrentMap<Long, ChannelGroup> groupChannelMap = new ConcurrentHashMap<>();

    @Resource
    private GroupService groupService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端 {} 与服务器建立连接", ctx.channel().id());
        channelGroup.add(ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端 {} 与服务器断开连接", ctx.channel().id());
        channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("收到客户端 {} 发送的消息：{}", ctx.channel().id(), msg.text());
        //将客户端发送的消息反序列化为ChatMessage
        Gson gson = new Gson();
        ChatMessage chatMessage = gson.fromJson(msg.text(), ChatMessage.class);
        String messageType = chatMessage.getMessageType();
        //客户端消息接受处理与转发
        Long receiverId = chatMessage.getReceiverId();
        switch (messageType) {
            case CONNECT_TYPE:
                //首次连接获取客户端的userId
                Long userId = chatMessage.getSenderId();
                log.info("chatHandler接收到的userId：{}", userId);
                //将用户id与对应的channel关联起来
                userChannelMap.put(userId, ctx.channel().id());
                List<Long> groupId = groupService.getGroupId(userId);
                //将用户id加入到所有groupId关联的channelGroup中
                for (Long id : groupId) {
                    //根据id获取channelGroup，如果没有就新建一个channelGroup
                    ChannelGroup group = groupChannelMap.computeIfAbsent(id, key -> new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
                    group.add(ctx.channel());
                }
                break;
            case SINGLE_TYPE:
                //消息类型为私聊
                //获取目标channelId
                ChannelId targetChannelId = userChannelMap.get(receiverId);
                if (targetChannelId == null) {
                    log.info("目标未上线");
                }
                Channel targetChannel = channelGroup.find(targetChannelId);
                //向目标用户发送消息
                targetChannel.writeAndFlush(new TextWebSocketFrame(msg.text()));
                break;
            case GROUP_TYPE:
                //消息类型是群聊
                //获取目标群聊的channelGroup，并向其发送消息
                groupChannelMap.get(receiverId).writeAndFlush(new TextWebSocketFrame(msg.text()));
                break;
            default: break;
        }
    }
}
