package com.security.mybatis.plus.securitymybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.mybatis.plus.securitymybatisplus.pojo.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TUserMapper extends BaseMapper<TUser> {
}
