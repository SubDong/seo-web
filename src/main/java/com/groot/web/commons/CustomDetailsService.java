package com.groot.web.commons;

import com.groot.commons.dto.SystemUserDTO;
import com.groot.web.services.SystemUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by subDong on 2015/2/11.
 */
public class CustomDetailsService implements UserDetailsService {
    public static String userName;
    @Resource
    private SystemUserService systemUserService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SystemUserDTO systemUser = systemUserService.findByUserName(s);
        UserDetails details = new User(systemUser.getUserName(), "", true, true, true, true, grantedAuthorities(1));
        if (details != null) {
            userName = details.getUsername();
        }
        return details;
    }

    private Collection<GrantedAuthority> grantedAuthorities(Integer access){
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(access.compareTo(1)==0){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }
    public static String getUserName(){
        return userName;
    }

}
