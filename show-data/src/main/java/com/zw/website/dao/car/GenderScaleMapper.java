package com.zw.website.dao.car;

import com.zw.website.model.car.GenderScale;
import com.zw.website.model.car.GenderScaleExample;
import java.util.List;

public interface GenderScaleMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gender_scale
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    List<GenderScale> selectByExample(GenderScaleExample example);
}