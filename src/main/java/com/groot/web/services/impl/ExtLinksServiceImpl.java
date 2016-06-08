package com.groot.web.services.impl;

import com.groot.commons.dto.ExtLinksDTO;
import com.groot.mongodb.dao.ExtLinksDAO;
import com.groot.web.services.ExtLinksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by subDong on 2015/1/8.
 */
@Service
public class ExtLinksServiceImpl implements ExtLinksService {
    @Resource
    private ExtLinksDAO extLinksDAO;

    @Override
    public ExtLinksDTO save(ExtLinksDTO dto) {
        ExtLinksDTO dtoFind = extLinksDAO.findByParams(new HashMap<String, Object>() {{
            put("url", dto.getUrl());
        }});
        if (dtoFind != null) {
            int max = extLinksDAO.getMaxCount(dto.getUrl());
            if (dto.getExtLinksCount() > max) {
                extLinksDAO.save(dto);
            }
        }
        else {
            extLinksDAO.save(dto);
        }
        return dto;
    }

    @Override
    public ExtLinksDTO findOne(String id) {
        return extLinksDAO.findOne(id);
    }

    @Override
    public ExtLinksDTO findParams(Map<String, Object> params) {
        return extLinksDAO.findByParams(params);
    }

    @Override
    public int getNearExtLinksCount(String url) {
        return extLinksDAO.getNearExtLinksCount(url);
    }


}
