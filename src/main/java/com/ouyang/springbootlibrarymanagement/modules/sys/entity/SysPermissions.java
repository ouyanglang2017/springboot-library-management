package com.ouyang.springbootlibrarymanagement.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_permissions")
public class SysPermissions {
    @TableId(type = IdType.UUID)
    private String id;
    private String permissionsName;
    private Date createTime;
    private Date updateTime;
}
