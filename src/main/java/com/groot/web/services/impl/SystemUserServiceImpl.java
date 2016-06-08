package com.groot.web.services.impl;

import com.groot.commons.dto.SystemUserDTO;
import com.groot.mongodb.dao.SystemDAO;
import com.groot.web.services.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by subDong on 2015/2/13.
 */
@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {
    @Resource
    private SystemDAO systemDAO;

    @Override
    public SystemUserDTO findByUserName(String userName) {
        return systemDAO.findByUserName(userName);
    }
}
