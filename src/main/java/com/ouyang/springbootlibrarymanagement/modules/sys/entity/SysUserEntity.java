package com.ouyang.springbootlibrarymanagement.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class SysUserEntity {
    @TableId(type = IdType.UUID)
    private String id;

    private String username;
    private String password;
    private String salt;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
