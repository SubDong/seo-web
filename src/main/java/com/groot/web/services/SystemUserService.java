package com.groot.web.services;

import com.groot.commons.dto.SystemUserDTO;

/**
 * Created by subDong on 2015/2/13.
 */
public interface SystemUserService {
    SystemUserDTO findByUserName(String userName);
}
