package com.ouyang.springbootlibrarymanagement.modules.sys.service.impl;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysRoleEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.mapper.SysRoleMapper;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoleEntity> getRolesByUser(SysUserEntity user) {
        return sysRoleMapper.getRolesByUserId(user.getId());
    }
}
