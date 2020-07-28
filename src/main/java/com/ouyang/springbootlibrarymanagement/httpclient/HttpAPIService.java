package com.ouyang.springbootlibrarymanagement.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HttpAPIService {
    @Autowired
    private CloseableHttpClient closeableHttpClient;
    @Autowired
    private RequestConfig config;

    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String doGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        CloseableHttpClient closeableHttpClient = this.closeableHttpClient;
        CloseableHttpResponse res = closeableHttpClient.execute(httpGet);
        if (res.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(res.getEntity(), "UTF-8");
        }
        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @param map
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String doGet(String url, Map<String, Object> map) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 带参数的post请求
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, Object> map) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        if (map != null) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String,Object> entry: map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list,"UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
        }
        CloseableHttpResponse response = this.closeableHttpClient.execute(httpPost);

        return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));

    }

    /**
     * 不带参数的post请求
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url) throws IOException {
        return this.doPost(url,null);
    }
}
