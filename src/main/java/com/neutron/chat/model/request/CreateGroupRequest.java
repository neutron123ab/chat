package com.neutron.chat.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzs
 * @date 2023/6/2 14:56
 */
@Data
public class CreateGroupRequest implements Serializable {

    /**
     * 群聊名称
     */
    private String groupName;

}
