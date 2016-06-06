package com.groot.mongodb.dao.impl;

import com.groot.commons.MongoEntityConstants;
import com.groot.commons.dto.ExtLinksDTO;
import com.groot.mongodb.dao.ExtLinksDAO;
import com.groot.mongodb.entity.ExtLinksEntity;
import com.groot.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

/**
 * Created by XiaoWei on 2015/1/8.
 */
@Repository("extLinksDAO")
public class ExtLinksDAOImpl extends BaseRepository<ExtLinksDTO, String> implements ExtLinksDAO {


    @Override
    public Class<ExtLinksEntity> getEntityClass() {
        return ExtLinksEntity.class;
    }

    @Override
    public Class<ExtLinksDTO> getDTOClass() {
        return ExtLinksDTO.class;
    }

    @Override
    public ExtLinksDTO findByParams(Map<String, Object> params) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params.size() > 0 && params != null)
            params.forEach((k, v) -> c.and(k).is(v));

        q.addCriteria(c);
        q.with(new Sort(Sort.Direction.DESC, "dsd"));
        ExtLinksEntity extLinksEntity = getMongoTemplate().findOne(q, getEntityClass());
        return ObjectUtils.convert(extLinksEntity, ExtLinksDTO.class);
    }

    @Override
    public int getMaxCount(String url) {
        int max = 0;
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("url").is(url)),
                project("elc"),
                group("elc").max("elc").as("elc")
        );
        AggregationResults<MaxVo> result = getMongoTemplate().aggregate(aggregation, MongoEntityConstants.TBL_EXTLINKS, MaxVo.class);
        List<MaxVo> finalResult = result.getMappedResults();
        max = finalResult.get(0).getElc();
        return max;
    }

    @Override
    public int getNearExtLinksCount(String url) {
        Query q = new Query(Criteria.where("url").is(url));
        q.with(new Sort(Sort.Direction.DESC, "sd"));
        List<ExtLinksEntity> result = getMongoTemplate().find(q, getEntityClass());

        return result.get(0).getExtLinksCount();
    }

    private class MaxVo {
        private int elc;

        public int getElc() {
            return elc;
        }

        public void setElc(int elc) {
            this.elc = elc;
        }

        @Override
        public String toString() {
            return "maxVo{" +
                    "elc=" + elc +
                    '}';
        }
    }
}
