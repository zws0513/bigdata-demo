package com.zw.website.service.car;

import com.zw.website.dao.car.GenderScaleMapper;
import com.zw.website.dao.car.OwnerTypeBrandMapper;
import com.zw.website.model.car.GenderScale;
import com.zw.website.model.car.GenderScaleExample;
import com.zw.website.model.car.OwnerTypeBrand;
import com.zw.website.model.car.OwnerTypeBrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class OwnerTypeBrandService {

    @Autowired
    private OwnerTypeBrandMapper dao;

    public List<OwnerTypeBrand> getAll() {
        OwnerTypeBrandExample parameter = new OwnerTypeBrandExample();
        parameter.setOrderByClause("owner asc, type asc, amount desc");
        List<OwnerTypeBrand> list = dao.selectByExample(parameter);
        return list;
    }
}
