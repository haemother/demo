package com.security.mybatis.plus.securitymybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.security.mybatis.plus.securitymybatisplus.mapper.TUserMapper;
import com.security.mybatis.plus.securitymybatisplus.pojo.TUser;
import com.security.mybatis.plus.securitymybatisplus.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TUserServiceImpl implements TUserService {

    @Autowired
    private TUserMapper tUserMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        1 query from database
        TUser param = new TUser();
        param.setUsername(username);
        TUser tUser = tUserMapper.selectOne(new QueryWrapper<>(param));

//        2 use if to judge the user whether exists
        if(tUser == null){
            System.out.println("Not exist the user.");
            throw new UsernameNotFoundException("Not exist the user");
        }
        System.out.println("query user information--username: " + username +
                ", password: " + tUser.getPassword());

//        3 password
        String password = bCryptPasswordEncoder.encode(tUser.getPassword());
        System.out.println("BCryptPasswordEncoder password: " + password);

//        4 granted authority list
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin1, admin2, ROLE_manager, ROLE_user");

//        5 create a user with username, password and granted authority list
        UserDetails userDetails = new User(username, password, grantedAuthorities);

//        6 return user details information
        return userDetails;
    }
}
