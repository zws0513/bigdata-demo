package com.zw.website.dao.car.impl;

import com.zw.framework.dao.AbstractDao;
import com.zw.website.dao.car.GenderScaleMapper;
import com.zw.website.model.car.GenderScale;
import com.zw.website.model.car.GenderScaleExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Repository
public class GenderScaleMapperImpl extends AbstractDao implements GenderScaleMapper {

    private static final String NAMESPACE = "com.zw.website.dao.car.GenderScaleMapper";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gender_scale
     *
     * @param example
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    @Override
    public List<GenderScale> selectByExample(GenderScaleExample example) {
        return this.getSqlSession().selectList(NAMESPACE + ".selectByExample");
    }
}
