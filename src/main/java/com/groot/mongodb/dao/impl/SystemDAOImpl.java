package com.groot.mongodb.dao.impl;

import com.groot.commons.dto.SystemUserDTO;
import com.groot.mongodb.dao.SystemDAO;
import com.groot.mongodb.entity.SystemUserEntity;
import com.groot.utils.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by XiaoWei on 2015/2/13.
 */
@Repository("SystemDAO")
public class SystemDAOImpl extends BaseRepository<SystemUserDTO,Long> implements SystemDAO {
    @Override
    public  Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Class<SystemUserDTO> getDTOClass() {
        return SystemUserDTO.class;
    }

    @Override
    public SystemUserDTO findByUserName(String u) {
        MongoTemplate mongoTemplate=getSystemMongoTemplate();
       SystemUserEntity entity= mongoTemplate.findOne(new Query(Criteria.where("userName").is(u)),getEntityClass(),"sys_user");
        return ObjectUtils.convert(entity,getDTOClass());
    }
}
