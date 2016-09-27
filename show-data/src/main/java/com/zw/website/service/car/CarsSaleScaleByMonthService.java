package com.zw.website.service.car;

import com.zw.website.dao.car.CarsSaleScaleByMonthMapper;
import com.zw.website.model.car.CarsSaleScaleByMonth;
import com.zw.website.model.car.CarsSaleScaleByMonthExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/24.
 */
@Service
public class CarsSaleScaleByMonthService {

    @Autowired
    private CarsSaleScaleByMonthMapper dao;

    public List<CarsSaleScaleByMonth> getAll() {
        CarsSaleScaleByMonthExample parameter = new CarsSaleScaleByMonthExample();
        parameter.setOrderByClause("month");
        List<CarsSaleScaleByMonth> list = dao.selectByExample(parameter);
        return list;
    }
}
