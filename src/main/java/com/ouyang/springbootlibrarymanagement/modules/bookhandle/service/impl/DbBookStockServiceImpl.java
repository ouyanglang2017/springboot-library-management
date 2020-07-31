package com.ouyang.springbootlibrarymanagement.modules.bookhandle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookStockEntity;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.mapper.DbBookStockMapper;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.service.IDbBookStockService;
import org.springframework.stereotype.Service;


@Service
public class DbBookStockServiceImpl extends ServiceImpl<DbBookStockMapper, DbBookStockEntity> implements IDbBookStockService {


}
