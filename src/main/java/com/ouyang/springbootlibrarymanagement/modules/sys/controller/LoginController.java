package com.ouyang.springbootlibrarymanagement.modules.sys.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.common.utils.PasswordUtil;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysUserService;
import com.ouyang.springbootlibrarymanagement.shiro.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private ISysUserService sysUserService;
    @PostMapping("/login")
    public Result<JSONObject> login(@RequestBody SysUserEntity sysUserEntity) {
        Result<JSONObject> result = new Result<>();
        String uname = sysUserEntity.getUsername();
        if (StringUtils.isEmpty(uname)){
            result.error500("参数缺失！");
            return result;
        }
        SysUserEntity user = sysUserService.getUserByUsername(sysUserEntity.getUsername());
        if (user == null){
            result.error500("用户不存在！");
            return result;
        }
        String salt = user.getSalt();
        String passwordEncode = PasswordUtil.encrypt(sysUserEntity.getUsername(), sysUserEntity.getPassword(), salt);
        if (!user.getPassword().equals(passwordEncode)){
            result.error500("账号或密码错误！");
            return result;
        }
        //生成一个token
        String token = JwtUtils.getToken(user.getUsername());
        user.setPassword("");
        user.setSalt("");
        JSONObject obj = new JSONObject();
        obj.put("token",token);
        obj.put("userInfo",user);
        result.setResult(obj);
        result.success("登录成功");
        return result;

    }
}
