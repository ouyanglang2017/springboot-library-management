package com.ouyang.springbootlibrarymanagement.modules.weixin.controller;

import com.alibaba.druid.util.StringUtils;
import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.weixin.SignConfigModel;
import com.ouyang.springbootlibrarymanagement.weixin.WeixinSignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixinAccess")
public class DbWeixinConfigController {
    @Autowired
    private WeixinSignUtils weixinSignUtils;

    //获取签名参数
    @RequestMapping("/getSignParams")
    public Result<SignConfigModel> getSign(String url){
        Result<SignConfigModel> result = new Result<>();
        if (StringUtils.isEmpty(url)){
            result.error500("参数为空");
            return result;
        }
        SignConfigModel signature = weixinSignUtils.getSignature(url);
        result.setSuccess(true);
        result.setResult(signature);
        return result;
    }
}
