package com.ouyang.springbootlibrarymanagement.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ouyang.springbootlibrarymanagement.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
    List<SysRoleEntity> getRolesByUserId(String userId);
}
