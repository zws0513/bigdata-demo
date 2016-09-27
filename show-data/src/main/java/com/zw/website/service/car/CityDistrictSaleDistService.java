package com.zw.website.service.car;

import com.zw.framework.controller.dto.NameIntValuePair;
import com.zw.website.dao.car.CarsSaleScaleByMonthMapper;
import com.zw.website.dao.car.CityDistrictSaleDistMapper;
import com.zw.website.model.car.CarsSaleScaleByMonth;
import com.zw.website.model.car.CarsSaleScaleByMonthExample;
import com.zw.website.model.car.CityDistrictSaleDist;
import com.zw.website.model.car.CityDistrictSaleDistExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class CityDistrictSaleDistService {

    @Autowired
    private CityDistrictSaleDistMapper dao;

    public List<CityDistrictSaleDist> getProvinceData() {
        List<CityDistrictSaleDist> list = dao.selectProvince(new CityDistrictSaleDistExample());
        return list;
    }

    public List<CityDistrictSaleDist> getCityData(String province) {
        CityDistrictSaleDistExample parameter = new CityDistrictSaleDistExample();
        parameter.createCriteria().andProvinceEqualTo(province);
        List<CityDistrictSaleDist> list = dao.selectCity(parameter);
        return list;
    }

    public List<CityDistrictSaleDist> getDistrictData(String province) {
        CityDistrictSaleDistExample parameter = new CityDistrictSaleDistExample();
        parameter.createCriteria().andProvinceEqualTo(province);
        List<CityDistrictSaleDist> list = dao.selectByExample(parameter);
        return list;
    }
}
