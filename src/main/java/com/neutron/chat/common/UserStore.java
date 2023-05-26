package com.neutron.chat.common;

import com.neutron.chat.model.dto.UserDTO;

/**
 * @author zzs
 * @date 2023/5/26 14:04
 */
public class UserStore {

    private UserStore() {
    }

    private static ThreadLocal<UserDTO> userThreadLocal = new ThreadLocal<>();

    public static UserDTO getUserThreadLocal() {
        return userThreadLocal.get();
    }

    public static void setUserThreadLocal(UserDTO userDTO) {
        userThreadLocal.set(userDTO);
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}
