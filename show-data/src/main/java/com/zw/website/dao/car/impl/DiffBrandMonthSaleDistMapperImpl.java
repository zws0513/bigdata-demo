package com.zw.website.dao.car.impl;

import com.zw.framework.dao.AbstractDao;
import com.zw.website.dao.car.DiffBrandMonthSaleDistMapper;
import com.zw.website.model.car.DiffBrandMonthSaleDist;
import com.zw.website.model.car.DiffBrandMonthSaleDistExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Repository
public class DiffBrandMonthSaleDistMapperImpl extends AbstractDao implements DiffBrandMonthSaleDistMapper {

    private static final String NAMESPACE = "com.zw.website.dao.car.DiffBrandMonthSaleDistMapper";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diff_brand_month_sale_dist
     *
     * @param example
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    @Override
    public List<DiffBrandMonthSaleDist> selectByExample(DiffBrandMonthSaleDistExample example) {
        return this.getSqlSession().selectList(NAMESPACE + ".selectByExample", example);
    }

    @Override
    public List<String> selectBrand() {
        return this.getSqlSession().selectList(NAMESPACE + ".selectBrand");
    }
}
