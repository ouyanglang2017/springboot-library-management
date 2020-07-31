package com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_book_info")
public class DbBookInfoEntity {
    @TableId
    private String isbn13;
    private String isbn10;
    private String cip;
    private String bookName;
    private String author;
    private String edition;
    private String binding;
    private String publisher;
    private String publishPlace;
    private String publishPrice;
    private String published;
    private String imgUrl;
    private String description;
    private Date createTime;
    private Date updateTime;
}
