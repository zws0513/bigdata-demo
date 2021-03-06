package com.zw.website.dao.car.impl;

import com.zw.framework.dao.AbstractDao;
import com.zw.website.dao.car.BrandEngineFuelMapper;
import com.zw.website.model.car.BrandEngineFuel;
import com.zw.website.model.car.BrandEngineFuelExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Repository
public class BrandEngineFuelMapperImpl extends AbstractDao implements BrandEngineFuelMapper {

    private static final String NAMESPACE = "com.zw.website.dao.car.BrandEngineFuelMapper";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_engine_fuel
     *
     * @param example
     *
     * @mbggenerated Sat Sep 24 19:34:11 CST 2016
     */
    @Override
    public List<BrandEngineFuel> selectByExample(BrandEngineFuelExample example) {
        return this.getSqlSession().selectList(NAMESPACE + ".selectByExample", example);
    }
}
