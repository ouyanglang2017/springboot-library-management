package com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_book_stock")
public class DbBookStockEntity {
    @TableId
    private String isbn13;
    private String isbn10;
    private Integer stock;
    private Date createTime;
    private Date updateTime;
}
