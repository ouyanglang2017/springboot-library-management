package com.ouyang.springbootlibrarymanagement.modules.bookhandle.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.httpclient.HttpAPIService;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookInfoEntity;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.service.IDbBookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class DbBookInfoServiceImpl implements IDbBookInfoService {
    @Value("${config.queryUrl}")
    private String queryUrl;

    @Autowired
    private HttpAPIService httpAPIService;

    @Override
    public Result<DbBookInfoEntity> scanBook(String isbn) throws IOException {
        Result<DbBookInfoEntity> result = new Result<>();
        if (StringUtils.isEmpty(isbn)) {
            result.error500("参数为空");
            return result;
        }
        try {
            //9787516820940
            String s = httpAPIService.doGet(queryUrl + isbn);
            DbBookInfoEntity dbBookInfoEntity = parsing(s);
            result.setResult(dbBookInfoEntity);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            result.error500("非法的参数");
            return result;
        }

    }

    private DbBookInfoEntity parsing(String resultStr) {
        DbBookInfoEntity dbBookInfoEntity = new DbBookInfoEntity();
        Document document = Jsoup.parse(resultStr);
        Element zw = document.getElementById("zw");
        Elements p = zw.getElementsByTag("p");
        String text = p.first().text();
        System.out.println(text);
        dbBookInfoEntity.setDescription(text);//书本描述
        Elements fll = zw.getElementsByClass("fll");
        Elements li = fll.eq(1).first().getElementsByTag("ul").first().getElementsByTag("li");
        String bookName = splitStr(li.eq(0).text());//书名
        dbBookInfoEntity.setBookName(bookName);
        String cip = splitStr(li.eq(1).text());//cip
        dbBookInfoEntity.setCip(cip);
        String isbn = splitStr(li.eq(2).text());//isbn
        dbBookInfoEntity.setIsbn13(isbn);
        String author = splitStr(li.eq(3).text());//作者
        dbBookInfoEntity.setAuthor(author);
        String publisher = splitStr(li.eq(4).text());//出版社
        dbBookInfoEntity.setPublisher(publisher);
        String publishPlace = splitStr(li.eq(5).text());//出版地
        dbBookInfoEntity.setPublishPlace(publishPlace);
        String published = splitStr(li.eq(6).text());//出版时间
        dbBookInfoEntity.setPublished(published);
        String publishPrice = splitStr(li.eq(7).text());//出版价格
        dbBookInfoEntity.setPublishPrice(publishPrice);
        return dbBookInfoEntity;
    }

    private String splitStr(String str) {
        String[] split = str.split("：");
        return split[1];
    }
}
