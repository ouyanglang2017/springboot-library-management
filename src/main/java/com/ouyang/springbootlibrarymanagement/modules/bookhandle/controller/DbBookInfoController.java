package com.ouyang.springbootlibrarymanagement.modules.bookhandle.controller;

import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookInfoEntity;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.service.IDbBookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/book")
@Slf4j
public class DbBookInfoController {
    @Autowired
    private IDbBookInfoService dbBookInfoService;

    /**
     * 扫描图书
     *
     * @param isbn
     * @return
     */
    @RequestMapping("/scan")
    public Result<DbBookInfoEntity> scanBook(String isbn) throws IOException {
        return dbBookInfoService.scanBook(isbn);
    }
}
