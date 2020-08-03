package com.ouyang.springbootlibrarymanagement.weixin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ouyang.springbootlibrarymanagement.httpclient.HttpAPIService;
import com.ouyang.springbootlibrarymanagement.modules.weixin.entity.DbWeixinConfigEntity;
import com.ouyang.springbootlibrarymanagement.modules.weixin.mapper.DbWeixinConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@Component
@Slf4j
public class WeixinSignUtils {
    final static String JSAPI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={ACCESS_TOKEN}&type=jsapi";
    @Autowired
    private AccessTokenUtils accessTokenUtils;
    @Autowired
    private HttpAPIService httpAPIService;
    @Autowired
    private DbWeixinConfigMapper dbWeixinConfigMapper;

    //获取随机字符串
    public String getNoncestr() {
        return UUID.randomUUID().toString();
    }

    //获取时间戳
    public String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    //获取JsApiTicket
    public String getJsapiTicket() {
        DbWeixinConfigEntity dbWeixinConfigEntity = accessTokenUtils.getWeixinConfig();
        //判断JsApiTicket是否过期
        long ticketExpiresIn = dbWeixinConfigEntity.getTicketExpiresIn();
        long now = System.currentTimeMillis();
        if (now < ticketExpiresIn) {
            //未过期
            return dbWeixinConfigEntity.getJsapiTicket();
        }
        //已过期
        String appid = dbWeixinConfigEntity.getAppid();

        String accessToken = accessTokenUtils.getAccessToken();
        if (accessToken != null) {
            String replace = JSAPI.replace("{ACCESS_TOKEN}", accessToken);
            try {
                String result = httpAPIService.doGet(replace);
                JSONObject content = (JSONObject) JSONObject.parse(result);
                Integer errcode = content.getInteger("errcode");
                if (errcode != null && errcode == 0) {
                    String ticket = content.getString("ticket");
                    long expiresIn = content.getLong("expires_in");

                    DbWeixinConfigEntity weixinConfigEntity = new DbWeixinConfigEntity();
                    weixinConfigEntity.setJsapiTicket(ticket);
                    weixinConfigEntity.setTicketExpiresIn(System.currentTimeMillis() + expiresIn * 1000);
                    QueryWrapper<DbWeixinConfigEntity> weixinConfigEntityQueryWrapper = new QueryWrapper<>();
                    weixinConfigEntityQueryWrapper.eq("appid", appid);
                    dbWeixinConfigMapper.update(weixinConfigEntity, weixinConfigEntityQueryWrapper);

                    return ticket;
                } else {
                    String errmsg = content.getString("errmsg");
                    log.error("获取token微信返回错误：errcode={},errmsg={}", errcode, errmsg);
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获取sign签名
    public SignConfigModel getSignature(String url) {
        DbWeixinConfigEntity weixinConfig = accessTokenUtils.getWeixinConfig();

        SignConfigModel signConfigModel = new SignConfigModel();
        String jsapiTicket = getJsapiTicket();
        String noncestr = getNoncestr();
        String timestamp = getTimestamp();
        //ASCII 码从小到大排序（字典序）
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("jsapi_ticket", jsapiTicket);
        sortedMap.put("noncestr", noncestr);
        sortedMap.put("timestamp", timestamp);
        sortedMap.put("url", url);
        StringBuffer stringBuffer = new StringBuffer();
        for (SortedMap.Entry<String, String> map : sortedMap.entrySet()) {
            String key = map.getKey();
            String value = map.getValue();
            stringBuffer.append(key + "=" + value + "&");
        }
        String string = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
        log.info("ASCII 码从小到大排序（字典序） string={}",string);
        //对string进行sha1签名，得到signature：
        String signature = DigestUtils.sha1Hex(string);
        signConfigModel.setAppId(weixinConfig.getAppid());
        signConfigModel.setNonceStr(noncestr);
        signConfigModel.setTimestamp(Long.valueOf(timestamp));
        signConfigModel.setSignature(signature);
        return signConfigModel;
    }
}
