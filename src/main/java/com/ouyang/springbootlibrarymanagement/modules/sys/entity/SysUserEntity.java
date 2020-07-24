package com.ouyang.springbootlibrarymanagement.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
