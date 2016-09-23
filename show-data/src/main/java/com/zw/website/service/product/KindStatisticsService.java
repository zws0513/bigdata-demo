package com.zw.website.service.product;

import com.zw.website.dao.product.KindStatisticsModelMapper;
import com.zw.website.dao.product.ProvinceModelMapper;
import com.zw.website.model.*;
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
    private KindStatisticsModelMapper kindDao;

    @Autowired
    private ProvinceModelMapper provinceDao;

    public List<KindStatisticsModel> getAll() {
        List<KindStatisticsModel> kindList = kindDao.selectByExample(new KindStatisticsModelExample());
        List<ProvinceModel> provinceList = provinceDao.selectByExample(new ProvinceModelExample());
        for (KindStatisticsModel kind : kindList) {
            for (int i = 0; i < provinceList.size(); i++) {
                if (kind.getProvince().startsWith(provinceList.get(i).getName())) {
                    provinceList.remove(i);
                }
            }
        }
        kindList.addAll(provinceList.stream().map(
                province -> new KindStatisticsModel(province.getName(), 0))
                .collect(Collectors.toList()));
        return kindList;
    }
}
