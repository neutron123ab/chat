package com.neutron.chat.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 通信消息实体类
 * @author zzs
 * @date 2023/5/25 19:17
 */
@Data
public class ChatMessage implements Serializable {

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 发送方id
     */
    private Long senderId;

    /**
     * 接收方（个人或群聊）id
     */
    private Long receiverId;

    /**
     * 消息内容
     */
    private String message;

}
