package com.ouyang.springbootlibrarymanagement.modules.sys.service.impl;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.mapper.SysUserMapper;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserEntity getUserByUsername(String username) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername(username);
        return sysUserMapper.selectOne(sysUserEntity);
    }
}
