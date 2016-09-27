package com.zw.website.service.car;

import com.zw.website.dao.car.CarsUsageAmountDistMapper;
import com.zw.website.model.car.CarsUsageAmountDist;
import com.zw.website.model.car.CarsUsageAmountDistExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class CarsUsageAmountDistService {

    @Autowired
    private CarsUsageAmountDistMapper dao;

    public List<CarsUsageAmountDist> getAll(String usage) {
        CarsUsageAmountDistExample parameter = new CarsUsageAmountDistExample();
//        parameter.createCriteria().andUsagesEqualTo(usage);
        parameter.setOrderByClause("usages");
        List<CarsUsageAmountDist> list = dao.selectByExample(parameter);
        return list;
    }
}
