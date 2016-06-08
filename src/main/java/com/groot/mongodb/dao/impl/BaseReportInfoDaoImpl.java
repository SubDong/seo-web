package com.groot.mongodb.dao.impl;

import com.groot.commons.MongoEntityConstants;
import com.groot.commons.dto.ReportInfoDTO;
import com.groot.mongodb.DBNameUtils;
import com.groot.mongodb.base.BaseMongoTemplate;
import com.groot.mongodb.dao.BaseReportInfoDao;
import com.groot.mongodb.entity.ReportInfoEntity;
import com.groot.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2015/2/2.
 */
@Repository("baseReportInfoDao")
public class BaseReportInfoDaoImpl implements BaseReportInfoDao {
    @Override
    public void insertInfo(ReportInfoDTO reportInfoDTO) {

        ReportInfoEntity reportInfoEntity = ObjectUtils.convert(reportInfoDTO, ReportInfoEntity.class);

        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getDatabaseName(reportInfoDTO.getAccount()));

        mongoTemplate.insert(reportInfoEntity, MongoEntityConstants.TBL_CRAWLING);
    }

    @Override
    public List<ReportInfoDTO> queryInfo(String userName,int endPer,int startPer) {

        MongoTemplate mongoTemplate = getMongoTempate(userName);

        Sort orders = new Sort(Sort.Direction.DESC,"date");

        List<ReportInfoEntity> reportInfoEntities = mongoTemplate.find(Query.query(Criteria.where("type").ne("1")).with(orders).skip(startPer).limit(endPer), ReportInfoEntity.class, MongoEntityConstants.TBL_CRAWLING);

        List<ReportInfoDTO> reportInfoDTO = ObjectUtils.convert(reportInfoEntities,ReportInfoDTO.class);

        return reportInfoDTO;
    }

    @Override
    public long getQueryInfoCount(String userName) {
        MongoTemplate mongoTemplate = getMongoTempate(userName);
        long count = mongoTemplate.count(new Query(), MongoEntityConstants.TBL_CRAWLING);
        return count;
    }

    @Override
    public Date getMaxDate(String userName) {

        Date date = null;

        MongoTemplate mongoTemplate = getMongoTempate(userName);

        Sort orders = new Sort(Sort.Direction.DESC,"date");

        List<ReportInfoEntity> reportInfoEntities = mongoTemplate.find(new Query().with(orders), ReportInfoEntity.class, MongoEntityConstants.TBL_CRAWLING);
        if(reportInfoEntities!= null && reportInfoEntities.size() != 0){
            date = reportInfoEntities.get(0).getDateTime();
        }
        return date;
    }

    @Override
    public void updateDate(String data, String uuid, String userName) {
        MongoTemplate mongoTemplate = getMongoTempate(userName);

        Update update = new Update();
        update.set("type",1);
        update.set("data",data);

        mongoTemplate.updateFirst(Query.query(Criteria.where("uuid").is(uuid).and("act").is(userName)),update,MongoEntityConstants.TBL_CRAWLING);
    }

    @Override
    public ReportInfoDTO getDownReport(String uid, String userName) {
        MongoTemplate mongoTemplate = getMongoTempate(userName);

        ReportInfoEntity reportInfoEntity = mongoTemplate.findOne(Query.query(Criteria.where("uuid").is(uid)), ReportInfoEntity.class, MongoEntityConstants.TBL_CRAWLING);

        return ObjectUtils.convert(reportInfoEntity,ReportInfoDTO.class);
    }

    private MongoTemplate getMongoTempate(String userName){
        return BaseMongoTemplate.getMongoTemplate(DBNameUtils.getDatabaseName(userName));
    }
}
