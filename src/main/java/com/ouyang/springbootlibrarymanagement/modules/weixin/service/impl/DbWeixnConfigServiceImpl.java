package com.ouyang.springbootlibrarymanagement.modules.weixin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ouyang.springbootlibrarymanagement.modules.weixin.entity.DbWeixinConfigEntity;
import com.ouyang.springbootlibrarymanagement.modules.weixin.mapper.DbWeixinConfigMapper;
import com.ouyang.springbootlibrarymanagement.modules.weixin.service.IDbWeixinConfigService;
import org.springframework.stereotype.Service;

@Service
public class DbWeixnConfigServiceImpl extends ServiceImpl<DbWeixinConfigMapper, DbWeixinConfigEntity> implements IDbWeixinConfigService {
}
