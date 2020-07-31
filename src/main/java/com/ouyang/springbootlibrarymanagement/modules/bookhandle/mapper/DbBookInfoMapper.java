package com.ouyang.springbootlibrarymanagement.modules.bookhandle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ouyang.springbootlibrarymanagement.modules.bookhandle.entity.DbBookInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DbBookInfoMapper extends BaseMapper<DbBookInfoEntity> {
}
