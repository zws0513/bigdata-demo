package com.zw.website.dao.product;

import com.zw.website.model.product.Province;
import com.zw.website.model.product.ProvinceExample;

import java.util.List;

public interface ProvinceMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province
     *
     * @mbggenerated Sat Sep 24 19:39:57 CST 2016
     */
    List<Province> selectByExample(ProvinceExample example);
}