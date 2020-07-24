package com.ouyang.springbootlibrarymanagement.modules.sys.service;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysRoleEntity;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;

import java.util.List;

public interface ISysRoleService {
    List<SysRoleEntity> getRolesByUser(SysUserEntity user);
}
