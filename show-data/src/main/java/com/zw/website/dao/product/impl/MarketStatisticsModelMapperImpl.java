package com.zw.website.dao.product.impl;

import com.zw.framework.dao.AbstractDao;
import com.zw.website.dao.product.MarketStatisticsModelMapper;
import com.zw.website.model.MarketStatisticsModel;
import com.zw.website.model.MarketStatisticsModelExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangws on 16/9/22.
 */
@Repository
public class MarketStatisticsModelMapperImpl extends AbstractDao implements MarketStatisticsModelMapper {

    private static final String NAMESPACE = "com.zw.website.dao.product.MarketStatisticsModelMapper";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_statistics
     *
     * @param example
     *
     * @mbggenerated Thu Sep 22 21:21:00 CST 2016
     */
    @Override
    public List<MarketStatisticsModel> selectByExample(MarketStatisticsModelExample example) {
        return this.getSqlSession().selectList(NAMESPACE + ".selectByExample");
    }
}
