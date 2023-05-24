package com.neutron.chat.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzs
 * @date 2023/5/23 21:40
 */
@Data
public class UserRegisterRequest implements Serializable {

    private String username;

    private String password;

    private String checkPassword;

}
