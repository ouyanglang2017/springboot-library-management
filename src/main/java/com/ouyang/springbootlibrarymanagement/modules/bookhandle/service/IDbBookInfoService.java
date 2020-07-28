package com.ouyang.springbootlibrarymanagement.modules.bookhandle.service;

import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookInfoEntity;

import java.io.IOException;

public interface IDbBookInfoService {

     Result<DbBookInfoEntity> scanBook(String isbn) throws IOException;
}
