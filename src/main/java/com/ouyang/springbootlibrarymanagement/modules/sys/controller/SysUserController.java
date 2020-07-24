package com.ouyang.springbootlibrarymanagement.modules.sys.controller;

import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.common.utils.PasswordUtil;
import com.ouyang.springbootlibrarymanagement.common.utils.oConvertUtils;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.mapper.SysUserMapper;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("sys/user")
public class SysUserController {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("/add")
    public Result<SysUserEntity> addUser() {
        Result<SysUserEntity> result = new Result<>();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername("admin");
        sysUserEntity.setPassword("admin");
        sysUserEntity.setCreateTime(new Date());
        sysUserEntity.setUpdateTime(new Date());
        String salt = oConvertUtils.randomGen(8);
        sysUserEntity.setSalt(salt);
        sysUserEntity.setStatus(1);
        String passwordEncode = PasswordUtil.encrypt(sysUserEntity.getUsername(), sysUserEntity.getPassword(), salt);
        sysUserEntity.setPassword(passwordEncode);
        sysUserMapper.insert(sysUserEntity);
        result.success("添加成功");
        result.setResult(sysUserEntity);
        return result;
    }


}
