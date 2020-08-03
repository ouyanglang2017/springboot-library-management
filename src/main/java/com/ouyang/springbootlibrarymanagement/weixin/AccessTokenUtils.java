package com.ouyang.springbootlibrarymanagement.weixin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ouyang.springbootlibrarymanagement.httpclient.HttpAPIService;
import com.ouyang.springbootlibrarymanagement.modules.weixin.entity.DbWeixinConfigEntity;
import com.ouyang.springbootlibrarymanagement.modules.weixin.mapper.DbWeixinConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AccessTokenUtils {
    final static String URI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    @Autowired
    private DbWeixinConfigMapper dbWeixinConfigMapper;
    @Autowired
    private HttpAPIService httpAPIService;

    //获取微信配置
    public DbWeixinConfigEntity getWeixinConfig() {
        QueryWrapper<DbWeixinConfigEntity> queryWrapper = new QueryWrapper<>();
        DbWeixinConfigEntity dbWeixinConfigEntity = dbWeixinConfigMapper.selectOne(queryWrapper);
        return dbWeixinConfigEntity;
    }

    //获取token
    public String getAccessToken() {
        DbWeixinConfigEntity dbWeixinConfigEntity = getWeixinConfig();
        //判断token是否过期
        long tokenExpiresIn = dbWeixinConfigEntity.getTokenExpiresIn();
        long now = System.currentTimeMillis();
        if (now < tokenExpiresIn) {
            //未过期
            return dbWeixinConfigEntity.getAccessToken();
        }
        //已过期
        String appid = dbWeixinConfigEntity.getAppid();
        String secret = dbWeixinConfigEntity.getSecret();
        String uriStr = URI.replace("APPID", appid).replace("APPSECRET", secret);

        try {
            String result = httpAPIService.doGet(uriStr);
            JSONObject content = (JSONObject) JSONObject.parse(result);
            String accessToken = content.getString("access_token");
            if (accessToken != null) {
                //更新数据库的token
                long expiresIn = content.getLong("expires_in");
                DbWeixinConfigEntity weixinConfigEntity = new DbWeixinConfigEntity();
                weixinConfigEntity.setAccessToken(accessToken);
                weixinConfigEntity.setTokenExpiresIn(System.currentTimeMillis() + expiresIn * 1000);
                QueryWrapper<DbWeixinConfigEntity> weixinConfigEntityQueryWrapper = new QueryWrapper<>();
                weixinConfigEntityQueryWrapper.eq("appid", appid);
                dbWeixinConfigMapper.update(weixinConfigEntity, weixinConfigEntityQueryWrapper);
                return accessToken;
            } else {
                Integer errcode = content.getInteger("errcode");
                if (errcode != null) {
                    String errmsg = content.getString("errmsg");
                    log.error("获取token微信返回错误：errcode={},errmsg={}", errcode, errmsg);
                    return null;
                }
                log.error("未知错误");
                return null;
            }

        } catch (IOException e) {
            log.error("请求微信token失败");
            e.printStackTrace();
        }
        return null;
    }

}
