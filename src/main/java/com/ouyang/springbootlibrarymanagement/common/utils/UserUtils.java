package com.ouyang.springbootlibrarymanagement.common.utils;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ouyang.springbootlibrarymanagement.shiro.JwtUtils.getUserNameByToken;

@Component
public class UserUtils {
    @Autowired
    private ISysUserService sysUserService;

    public  String getUserName() {
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String username = getUserNameByToken(token);
        return username;
    }

    public  SysUserEntity getUser() {
        SysUserEntity user = sysUserService.getUserByUsername(getUserName());
        return user;
    }

    public  String getUserId() {
        return getUser().getId();
    }
}
