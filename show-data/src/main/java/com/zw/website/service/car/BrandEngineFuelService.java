package com.zw.website.service.car;

import com.zw.website.dao.car.BrandEngineFuelMapper;
import com.zw.website.dao.car.DiffBrandMonthSaleDistMapper;
import com.zw.website.model.car.BrandEngineFuel;
import com.zw.website.model.car.BrandEngineFuelExample;
import com.zw.website.model.car.DiffBrandMonthSaleDist;
import com.zw.website.model.car.DiffBrandMonthSaleDistExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class BrandEngineFuelService {

    @Autowired
    private BrandEngineFuelMapper dao;

    public List<BrandEngineFuel> getAll() {
        BrandEngineFuelExample parameter = new BrandEngineFuelExample();
        parameter.setOrderByClause("brand ASC, engine ASC, fuel ASC, amount ASC");
        List<BrandEngineFuel> list = dao.selectByExample(parameter);
        return list;
    }
}
