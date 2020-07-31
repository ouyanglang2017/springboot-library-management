package com.ouyang.springbootlibrarymanagement.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
