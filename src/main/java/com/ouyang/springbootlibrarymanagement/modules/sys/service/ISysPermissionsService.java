package com.ouyang.springbootlibrarymanagement.modules.sys.service;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysPermissions;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysRoleEntity;

import java.util.List;

public interface ISysPermissionsService {
    List<SysPermissions> getPermissionsByRole(SysRoleEntity role);
}
