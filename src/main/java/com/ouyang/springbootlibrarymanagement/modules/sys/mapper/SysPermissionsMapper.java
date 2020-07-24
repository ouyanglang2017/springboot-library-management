package com.ouyang.springbootlibrarymanagement.modules.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysPermissions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionsMapper extends BaseMapper<SysPermissions> {
    List<SysPermissions> getPermissionsByRoleId(String roleId);
}
