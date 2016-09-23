package com.zw.website.dao.product.impl;

import com.zw.framework.dao.AbstractDao;
import com.zw.website.dao.product.PriceStatisticsModelMapper;
import com.zw.website.model.PriceStatisticsModel;
import com.zw.website.model.PriceStatisticsModelExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangws on 16/9/22.
 */
@Repository
public class PriceStatisticsModelMapperImpl extends AbstractDao implements PriceStatisticsModelMapper {

    private static final String NAMESPACE = "com.zw.website.dao.product.PriceStatisticsModelMapper";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_statistics
     *
     * @param example
     *
     * @mbggenerated Thu Sep 22 21:21:00 CST 2016
     */
    @Override
    public List<PriceStatisticsModel> selectByExample(PriceStatisticsModelExample example) {
        return this.getSqlSession().selectList(NAMESPACE + ".selectByExample");
    }
}
