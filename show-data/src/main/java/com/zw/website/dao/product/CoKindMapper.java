package com.zw.website.dao.product;

import com.zw.website.model.product.CoKind;
import com.zw.website.model.product.CoKindExample;

import java.util.List;

public interface CoKindMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_kind
     *
     * @mbggenerated Sat Sep 24 19:39:57 CST 2016
     */
    List<CoKind> selectByExample(CoKindExample example);
}