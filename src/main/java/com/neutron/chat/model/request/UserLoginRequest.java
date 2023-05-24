package com.neutron.chat.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzs
 * @date 2023/5/23 21:29
 */
@Data
public class UserLoginRequest implements Serializable {

    private String username;

    private String password;

}
