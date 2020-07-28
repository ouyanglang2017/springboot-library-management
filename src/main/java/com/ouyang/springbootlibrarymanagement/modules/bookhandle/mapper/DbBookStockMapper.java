package com.ouyang.springbootlibrarymanagement.modules.bookhandle.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookStockEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DbBookStockMapper extends BaseMapper<DbBookStockEntity> {
}
