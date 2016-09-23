package com.zw.website.service.product;

import com.zw.website.dao.product.PriceStatisticsModelMapper;
import com.zw.website.model.PriceStatisticsModel;
import com.zw.website.model.PriceStatisticsModelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/22.
 */
@Service
public class PriceStatisticsService {

    @Autowired
    private PriceStatisticsModelMapper dao;

    public List<PriceStatisticsModel> getAll() {
        PriceStatisticsModelExample priceStatisticsModelExample = new PriceStatisticsModelExample();
        priceStatisticsModelExample.setOrderByClause("kind, collect_date");
        return dao.selectByExample(priceStatisticsModelExample);
    }
}
