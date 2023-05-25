package com.neutron.chat.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzs
 * @date 2023/5/25 16:37
 */
@Data
public class JoinGroupRequest implements Serializable {

    private Long userId;

    private Long groupId;

}
