package com.neutron.chat.chatServer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 解析websocket连接uri中参数的处理器
 * @author zzs
 * @date 2023/5/26 15:29
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class UriHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());
        String userId = decoder.parameters().get("userId").get(0);
        AttributeKey<Long> paramKey = AttributeKey.valueOf("userId");
        ctx.channel().attr(paramKey).set(Long.parseLong(userId));
        log.info("uriHandler接收到的userId：{}", userId);
        ctx.fireChannelActive();
    }
}
