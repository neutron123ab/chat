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
     * 发送方用户名
     */
    private String senderName;

    /**
     * 接收方（个人或群聊）id
     */
    private Long receiverId;

    /**
     * 接收方名称（用户名或群聊名）
     */
    private String receiverName;

    /**
     * 消息内容
     */
    private String message;

}
