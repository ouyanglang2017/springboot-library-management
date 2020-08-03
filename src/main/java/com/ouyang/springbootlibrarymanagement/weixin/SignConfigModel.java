package com.ouyang.springbootlibrarymanagement.weixin;

import lombok.Data;

@Data
public class SignConfigModel {
    private String appId;
    private Long timestamp;
    private String nonceStr;
    private String signature;
}
