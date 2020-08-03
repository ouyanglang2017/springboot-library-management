package com.ouyang.springbootlibrarymanagement.modules.weixin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_weixin_config")
public class DbWeixinConfigEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String appid;
    private String secret;
    private String accessToken;
    private Long tokenExpiresIn;
    private String jsapiTicket;
    private Long ticketExpiresIn;
    private Date createTime;
    private Date updateTime;

}
