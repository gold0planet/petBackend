package com.dlnu.pet.util;

import com.dlnu.pet.pojo.entity.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static LoginUser getLoginUser() {
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getLoginUserId() {
        return getLoginUser().getUserId();
    }
}
