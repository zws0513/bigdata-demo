package com.zw.website.dao.car;

import com.zw.website.model.car.CityDistrictSaleDist;
import com.zw.website.model.car.CityDistrictSaleDistExample;
import java.util.List;

public interface CityDistrictSaleDistMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table city_district_sale_dist
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    List<CityDistrictSaleDist> selectByExample(CityDistrictSaleDistExample example);

    List<CityDistrictSaleDist> selectProvince(CityDistrictSaleDistExample example);

    List<CityDistrictSaleDist> selectCity(CityDistrictSaleDistExample example);
}