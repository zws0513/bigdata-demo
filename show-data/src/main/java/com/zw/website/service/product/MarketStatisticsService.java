package com.zw.website.service.product;

import com.zw.website.dao.product.MarketStatisticsMapper;
import com.zw.website.dao.product.ProvinceMapper;
import com.zw.website.model.product.MarketStatistics;
import com.zw.website.model.product.MarketStatisticsExample;
import com.zw.website.model.product.Province;
import com.zw.website.model.product.ProvinceExample;
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
    private MarketStatisticsMapper marketDao;

    @Autowired
    private ProvinceMapper provinceDao;

    public List<MarketStatistics> getAll() {
        List<MarketStatistics> marketList = marketDao.selectByExample(new MarketStatisticsExample());
        List<Province> provinceList = provinceDao.selectByExample(new ProvinceExample());
        for (MarketStatistics market : marketList) {
            for (int i = 0; i < provinceList.size(); i++) {
                if (market.getProvince().startsWith(provinceList.get(i).getName())) {
                    provinceList.remove(i);
                }
            }
        }
        marketList.addAll(provinceList.stream().map(
                province -> new MarketStatistics(province.getName(), 0))
                .collect(Collectors.toList()));
        return marketList;
    }
}
