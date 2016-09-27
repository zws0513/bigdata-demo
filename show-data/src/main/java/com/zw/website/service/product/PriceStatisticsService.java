package com.zw.website.service.product;

import com.zw.website.dao.product.PriceStatisticsMapper;
import com.zw.website.model.product.PriceStatistics;
import com.zw.website.model.product.PriceStatisticsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangws on 16/9/22.
 */
@Service
public class PriceStatisticsService {

    @Autowired
    private PriceStatisticsMapper dao;

    public List<PriceStatistics> getAll() {
        PriceStatisticsExample priceStatisticsModelExample = new PriceStatisticsExample();
        priceStatisticsModelExample.setOrderByClause("kind, collect_date");
        return dao.selectByExample(priceStatisticsModelExample);
    }
}
