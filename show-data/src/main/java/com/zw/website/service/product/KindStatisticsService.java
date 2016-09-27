package com.zw.website.service.product;

import com.zw.website.dao.product.KindStatisticsMapper;
import com.zw.website.dao.product.ProvinceMapper;
import com.zw.website.model.product.KindStatistics;
import com.zw.website.model.product.KindStatisticsExample;
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
public class KindStatisticsService {

    @Autowired
    private KindStatisticsMapper kindDao;

    @Autowired
    private ProvinceMapper provinceDao;

    public List<KindStatistics> getAll() {
        List<KindStatistics> kindList = kindDao.selectByExample(new KindStatisticsExample());
        List<Province> provinceList = provinceDao.selectByExample(new ProvinceExample());
        for (KindStatistics kind : kindList) {
            for (int i = 0; i < provinceList.size(); i++) {
                if (kind.getProvince().startsWith(provinceList.get(i).getName())) {
                    provinceList.remove(i);
                }
            }
        }
        kindList.addAll(provinceList.stream().map(
                province -> new KindStatistics(province.getName(), 0))
                .collect(Collectors.toList()));
        return kindList;
    }
}
