package com.ouyang.springbootlibrarymanagement.modules.sys.service;

import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysUserEntity;

public interface ISysUserService  {
    SysUserEntity getUserByUsername(String username);
}
