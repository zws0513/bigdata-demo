package com.zw.website.dao.product;

import com.zw.website.model.product.KindStatistics;
import com.zw.website.model.product.KindStatisticsExample;

import java.util.List;

public interface KindStatisticsMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kind_statistics
     *
     * @mbggenerated Sat Sep 24 19:39:57 CST 2016
     */
    List<KindStatistics> selectByExample(KindStatisticsExample example);
}