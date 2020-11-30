package com.mybatis.demo.security;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.repository.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(AppUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Trying to authenticate user :: ", username);
        Optional<User> user = Optional.ofNullable(userMapper.findByUserName(username));
        user.orElseThrow(() -> new UsernameNotFoundException("User Not Found " + username));
        return user.map(AppUser::new).get();
    }
}
