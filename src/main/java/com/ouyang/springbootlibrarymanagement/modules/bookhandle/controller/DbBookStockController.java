package com.ouyang.springbootlibrarymanagement.modules.bookhandle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ouyang.springbootlibrarymanagement.common.api.Result;
import com.ouyang.springbootlibrarymanagement.common.utils.UserUtils;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookStockEntity;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.service.IDbBookStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookStock")
@Slf4j
public class DbBookStockController {
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private IDbBookStockService dbBookStockService;

    @RequestMapping("/list")
    public Result<IPage<DbBookStockEntity>> queryList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<DbBookStockEntity>> result = new Result<>();
        Page<DbBookStockEntity> page = new Page<>();
        DbBookStockEntity dbBookStockEntity = new DbBookStockEntity();
        dbBookStockEntity.setSysUserId(userUtils.getUserId());
        QueryWrapper<DbBookStockEntity> queryWrapper = new QueryWrapper<>(dbBookStockEntity);
        IPage<DbBookStockEntity> pageList = dbBookStockService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;

    }
}
