package com.ouyang.springbootlibrarymanagement.modules.sys.service.impl;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysPermissions;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysRoleEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.mapper.SysPermissionsMapper;
import com.ouyang.springbootlibrarymanagement.modules.sys.service.ISysPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionsServiceImpl implements ISysPermissionsService {
    @Autowired
    private SysPermissionsMapper sysPermissionsMapper;

    @Override
    public List<SysPermissions> getPermissionsByRole(SysRoleEntity role) {
        return sysPermissionsMapper.getPermissionsByRoleId(role.getId());
    }
}
