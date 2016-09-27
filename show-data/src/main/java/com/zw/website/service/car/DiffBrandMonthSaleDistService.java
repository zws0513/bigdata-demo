package com.zw.website.service.car;

import com.zw.website.dao.car.DiffBrandMonthSaleDistMapper;
import com.zw.website.dao.car.OwnerTypeBrandMapper;
import com.zw.website.model.car.DiffBrandMonthSaleDist;
import com.zw.website.model.car.DiffBrandMonthSaleDistExample;
import com.zw.website.model.car.OwnerTypeBrand;
import com.zw.website.model.car.OwnerTypeBrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class DiffBrandMonthSaleDistService {

    @Autowired
    private DiffBrandMonthSaleDistMapper dao;

    public List<DiffBrandMonthSaleDist> getBrandData(String brand) {
        DiffBrandMonthSaleDistExample parameter = new DiffBrandMonthSaleDistExample();
        parameter.createCriteria().andBrandEqualTo(brand);
        List<DiffBrandMonthSaleDist> list = dao.selectByExample(parameter);
        return list;
    }

    public List<String> getBrand() {
        return dao.selectBrand();
    }
}
