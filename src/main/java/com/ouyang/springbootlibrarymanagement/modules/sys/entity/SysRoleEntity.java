package com.ouyang.springbootlibrarymanagement.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_role")
public class SysRoleEntity {
    @TableId(type = IdType.UUID)
    private String id;
    private String roleName;
    private Date createTime;
    private Date updateTime;
}
