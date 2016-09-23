package com.zw.website.service.product;

import com.zw.website.dao.product.MarketStatisticsModelMapper;
import com.zw.website.dao.product.ProvinceModelMapper;
import com.zw.website.model.MarketStatisticsModel;
import com.zw.website.model.MarketStatisticsModelExample;
import com.zw.website.model.ProvinceModel;
import com.zw.website.model.ProvinceModelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangws on 16/9/22.
 */
@Service
public class MarketStatisticsService {

    @Autowired
    private MarketStatisticsModelMapper marketDao;

    @Autowired
    private ProvinceModelMapper provinceDao;

    public List<MarketStatisticsModel> getAll() {
        List<MarketStatisticsModel> marketList = marketDao.selectByExample(new MarketStatisticsModelExample());
        List<ProvinceModel> provinceList = provinceDao.selectByExample(new ProvinceModelExample());
        for (MarketStatisticsModel market : marketList) {
            for (int i = 0; i < provinceList.size(); i++) {
                if (market.getProvince().startsWith(provinceList.get(i).getName())) {
                    provinceList.remove(i);
                }
            }
        }
        marketList.addAll(provinceList.stream().map(
                province -> new MarketStatisticsModel(province.getName(), 0))
                .collect(Collectors.toList()));
        return marketList;
    }
}
