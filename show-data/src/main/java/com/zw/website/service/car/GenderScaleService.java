package com.zw.website.service.car;

import com.zw.website.dao.car.CityDistrictSaleDistMapper;
import com.zw.website.dao.car.GenderScaleMapper;
import com.zw.website.model.car.CityDistrictSaleDist;
import com.zw.website.model.car.CityDistrictSaleDistExample;
import com.zw.website.model.car.GenderScale;
import com.zw.website.model.car.GenderScaleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class GenderScaleService {

    @Autowired
    private GenderScaleMapper dao;

    public List<GenderScale> getAll() {
        GenderScaleExample parameter = new GenderScaleExample();
        List<GenderScale> list = dao.selectByExample(parameter);
        return list;
    }
}
