package com.neutron.chat.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 群组表
 * @author zzs
 * @TableName group
 */
@TableName(value ="t_group")
@Data
public class Group implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 群主id
     */
    private Long groupOwnerId;

    /**
     * 群组名
     */
    private String groupName;

    /**
     * 群聊头像
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除（0-正常，1-已删除）
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}